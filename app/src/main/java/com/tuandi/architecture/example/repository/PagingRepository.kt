package com.tuandi.architecture.example.repository

import androidx.annotation.WorkerThread
import com.tuandi.architecture.example.network.api.GithubApi
import com.tuandi.architecture.example.network.models.Repo
import com.tuandi.architecture.extensions.safeApiCall
import com.tuandi.architecture.extensions.suspendOnFailure
import com.tuandi.architecture.extensions.suspendOnSuccess
import com.tuandi.architecture.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

const val IN_QUALIFIER = "in:name,description"
private const val NETWORK_PAGE_SIZE = 20
private const val GITHUB_STARTING_PAGE_INDEX = 1

class PagingRepository @Inject constructor(
    private val githubApi: GithubApi
) {


    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    // keep the list of all results received
    private val inMemoryCache = mutableListOf<Repo>()

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = GITHUB_STARTING_PAGE_INDEX

    suspend fun requestMore(query: String) {
        if (isRequestInProgress) return
        val successful = requestAndSaveData(query)
        if (successful) {
            lastRequestedPage++
        }
    }



    @WorkerThread
    private suspend fun requestAndSaveData(query: String): Boolean {
        isRequestInProgress = true
        var successful = false
        val apiQuery = query + IN_QUALIFIER
        safeApiCall(Dispatchers.IO) {
            githubApi.searchRepos(apiQuery, lastRequestedPage, NETWORK_PAGE_SIZE)
        }.suspendOnSuccess {
            val repos = this.items
            inMemoryCache.addAll(repos)
            val reposByName = reposByName()
            searchResults.emit(Result.Success(reposByName))
            successful = true
        }.suspendOnFailure {
            searchResults.emit(this)
        }
        isRequestInProgress = false
        return successful
    }

    // shared flow of results, which allows us to broadcast updates so
    // the subscriber will have the latest data
    private val searchResults = MutableSharedFlow<Result<List<Repo>>>()

    /**
     * Search repositories whose names match the query, exposed as a stream of data that will emit
     * every time we get more data from the network.
     */
    suspend fun getSearchResultStream(): Flow<Result<List<Repo>>> {
        lastRequestedPage = 1
        inMemoryCache.clear()
        requestAndSaveData(query)
        return searchResults
    }

    private fun reposByName(): List<Repo> {
        return inMemoryCache.filter {
            it.name.contains(query, true) ||
                    (it.description != null && it.description.contains(query, true))
        }.sortedWith(compareByDescending<Repo> { it.stars }.thenBy { it.name })
    }

    val query = "android"

    suspend fun loadPage(page: Int) {
        isRequestInProgress = true
        var successful = false
        val apiQuery = query + IN_QUALIFIER
        safeApiCall(Dispatchers.IO) {
            githubApi.searchRepos(apiQuery, page, NETWORK_PAGE_SIZE)
        }.suspendOnSuccess {
            val repos = this.items
            inMemoryCache.addAll(repos)
            val reposByName = reposByName()
            searchResults.emit(Result.Success(reposByName))
            successful = true
        }.suspendOnFailure {
            searchResults.emit(this)
        }
        isRequestInProgress = false
    }
}

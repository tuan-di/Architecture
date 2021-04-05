package com.tuandi.architecture.example.repository

import com.tuandi.architecture.example.network.api.PokemonApi
import com.tuandi.architecture.example.network.models.Pokemon
import com.tuandi.architecture.extensions.safeApiCall
import com.tuandi.architecture.extensions.suspendOnFailure
import com.tuandi.architecture.extensions.suspendOnSuccess
import com.tuandi.architecture.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

private const val PAGING_SIZE = 20

class MainRepository @Inject constructor(
    private val pokemonApi: PokemonApi
) {

    suspend fun fetchPokemonList(page: Int) = safeApiCall(Dispatchers.IO) {
        pokemonApi.fetchPokemonList(
            limit = PAGING_SIZE,
            offset = page * PAGING_SIZE
        )
    }.suspendOnSuccess {
        val data = this.results
        searchResults.emit(Result.Success(data))
    }.suspendOnFailure {
        searchResults.emit(this)
    }

    private val searchResults = MutableSharedFlow<Result<List<Pokemon>>>(replay = 1)

    fun fetchPokemonListStream(): Flow<Result<List<Pokemon>>> {
        return searchResults
    }
}

package com.tuandi.architecture.example.ui.fragments.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.tuandi.architecture.base.BaseViewModel
import com.tuandi.architecture.example.network.models.Repo
import com.tuandi.architecture.example.repository.PagingRepository
import com.tuandi.architecture.network.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val VISIBLE_THRESHOLD = 5

@HiltViewModel
class PagingViewModel @Inject constructor(private val repository: PagingRepository) :
    BaseViewModel() {

    val repoResult: LiveData<Result<List<Repo>>> =
        liveData {
            val repos = repository.getSearchResultStream("android").asLiveData(Dispatchers.Main)
            emitSource(repos)
        }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            requestRepos()
        }
    }

    init {
        requestRepos()
    }

    private fun requestRepos(): Unit {
        viewModelScope.launch {
            repository.requestMore("android")
        }
    }
}


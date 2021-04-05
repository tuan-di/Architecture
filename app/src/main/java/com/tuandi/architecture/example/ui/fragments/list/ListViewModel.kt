package com.tuandi.architecture.example.ui.fragments.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.tuandi.architecture.base.BaseViewModel
import com.tuandi.architecture.example.network.models.Pokemon
import com.tuandi.architecture.example.repository.MainRepository
import com.tuandi.architecture.network.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val repository: MainRepository) : BaseViewModel() {

    val pokemonList: LiveData<Result<List<Pokemon>>> =
        liveData {
            val repos = repository.fetchPokemonListStream().asLiveData(Dispatchers.Main)
            emitSource(repos)
        }

    fun getPokemon(page: Int = 0) {
        viewModelScope.launch {
            showLoading()
            repository.fetchPokemonList(page)
            hideLoading()
        }
    }
}


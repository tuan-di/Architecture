package com.tuandi.architecture.example.ui.fragments.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tuandi.architecture.base.BaseViewModel
import com.tuandi.architecture.example.network.models.Pokemon
import com.tuandi.architecture.example.repository.MainRepository
import com.tuandi.architecture.network.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val repository: MainRepository) : BaseViewModel() {

//    val pokemonList: LiveData<Result<List<Pokemon>>> =
//        liveData {
//            val repos = repository.fetchPokemonListStream().asLiveData(Dispatchers.Main)
//            emitSource(repos)
//        }

    private val _pokemonList = MutableLiveData<Result<List<Pokemon>>>()
    val pokemonList: LiveData<Result<List<Pokemon>>> get() = _pokemonList

    fun getPokemon(page: Int = 0) {
        viewModelScope.launch {
            repository.fetchPokemonList(page).collect {
                _pokemonList.value = it
                hideLoading()
            }
        }
    }
}


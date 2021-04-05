package com.tuandi.architecture.example.ui.fragments.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tuandi.architecture.base.BaseViewModel
import com.tuandi.architecture.example.network.models.Pokemon
import com.tuandi.architecture.example.repository.MainRepository
import com.tuandi.architecture.extensions.onError
import com.tuandi.architecture.extensions.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val repository: MainRepository) : BaseViewModel() {
    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> get() = _pokemonList

    fun getPokemon(page: Int = 0) {
        viewModelScope.launch {
            showLoading()
            repository.fetchPokemonList(page)
                .onError {
                    Timber.e(this.errorMessage)
                }.onSuccess {
                    _pokemonList.postValue(this.results)
                }
            hideLoading()
        }
    }
}


package com.tuandi.architecture.example.ui.fragments.detail

import androidx.lifecycle.*
import com.tuandi.architecture.base.BaseViewModel
import com.tuandi.architecture.example.network.models.PokemonInfo
import com.tuandi.architecture.example.repository.MainRepository
import com.tuandi.architecture.extensions.onSuccess
import com.tuandi.architecture.network.Result
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel @AssistedInject constructor(
    @Assisted private val pokemonName: String,
    private val repository: MainRepository
) :
    BaseViewModel() {

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(pokemonName: String): DetailViewModel
    }

//    val pokemonInfo: LiveData<Result<PokemonInfo>> =
//        repository.pokemonInfo(pokemonName).onStart { showLoading() }.onCompletion { hideLoading() }
//            .asLiveData()


    //Way 2
    private val _pokemonInfo = MutableLiveData<PokemonInfo>()
    val pokemonInfo: LiveData<PokemonInfo> get() = _pokemonInfo

    fun getPokemonInfo() {
        viewModelScope.launch {
            repository.pokemonInfo(pokemonName).collect {
                it.onSuccess {
                    _pokemonInfo.value = this
                }
            }
        }
    }
    //Way 2

    init {
        getPokemonInfo()
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            pokemonName: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(pokemonName) as T
            }
        }
    }
}
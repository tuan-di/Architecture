package com.tuandi.architecture.example.ui.fragments.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.tuandi.architecture.base.BaseViewModel
import com.tuandi.architecture.example.network.models.PokemonInfo
import com.tuandi.architecture.example.repository.MainRepository
import com.tuandi.architecture.network.Result
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

class DetailViewModel @AssistedInject constructor(
    @Assisted private val pokemonName: String,
    private val repository: MainRepository
) :
    BaseViewModel() {

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(pokemonName: String): DetailViewModel
    }

    val pokemonInfo: LiveData<Result<PokemonInfo>> =
        liveData {
            repository.pokemonInfo(pokemonName)
            Timber.e("123")
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
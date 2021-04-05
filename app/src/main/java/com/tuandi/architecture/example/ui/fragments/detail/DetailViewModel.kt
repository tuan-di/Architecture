package com.tuandi.architecture.example.ui.fragments.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tuandi.architecture.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class DetailViewModel @AssistedInject constructor(@Assisted private val pokemonName: String) :
    BaseViewModel() {

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(pokemonName: String): DetailViewModel
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
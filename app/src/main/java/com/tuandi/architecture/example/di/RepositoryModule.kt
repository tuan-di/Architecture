package com.tuandi.architecture.example.di

import com.tuandi.architecture.example.network.api.PokemonApi
import com.tuandi.architecture.example.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideMainRepository(
        pokemonApi: PokemonApi
    ): MainRepository {
        return MainRepository(pokemonApi)
    }
}

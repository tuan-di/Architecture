package com.tuandi.architecture.example.repository

import androidx.annotation.WorkerThread
import com.tuandi.architecture.example.network.api.PokemonApi
import com.tuandi.architecture.extensions.safeApiCall
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

private const val PAGING_SIZE = 20

class MainRepository @Inject constructor(
    private val pokemonApi: PokemonApi
) {

    @WorkerThread
    suspend fun fetchPokemonList(page: Int, ) = safeApiCall(Dispatchers.IO) {
        pokemonApi.fetchPokemonList(
            limit = PAGING_SIZE,
            offset = page * PAGING_SIZE
        )
    }
}

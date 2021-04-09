package com.tuandi.architecture.example.repository

import com.tuandi.architecture.example.network.api.PokemonApi
import com.tuandi.architecture.extensions.onFailure
import com.tuandi.architecture.extensions.onSuccess
import com.tuandi.architecture.extensions.safeApiCall
import com.tuandi.architecture.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val PAGING_SIZE = 20

class MainRepository @Inject constructor(
    private val pokemonApi: PokemonApi
) {
    fun fetchPokemonList(page: Int) = flow {
        safeApiCall(Dispatchers.IO) {
            pokemonApi.fetchPokemonList(
                limit = PAGING_SIZE,
                offset = page * PAGING_SIZE
            )
        }.onFailure {
            emit(this)
        }.onSuccess {
            emit(Result.Success(this.results))
        }
    }

    fun pokemonInfo(name: String) = flow {
        //emit(Loading)
        val res = safeApiCall(Dispatchers.IO) { pokemonApi.fetchPokemonInfo(name) }
        emit(res)
    }
}

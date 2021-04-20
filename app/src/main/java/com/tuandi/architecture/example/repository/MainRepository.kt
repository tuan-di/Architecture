package com.tuandi.architecture.example.repository

import androidx.annotation.WorkerThread
import com.tuandi.architecture.example.network.api.PokemonApi
import com.tuandi.architecture.example.network.models.Pokemon
import com.tuandi.architecture.extensions.onFailure
import com.tuandi.architecture.extensions.onSuccess
import com.tuandi.architecture.extensions.safeApiCall
import com.tuandi.architecture.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

private const val PAGING_SIZE = 20

class MainRepository @Inject constructor(
    private val pokemonApi: PokemonApi
) {
    @WorkerThread
    fun fetchPokemonList(page: Int): Flow<Result<List<Pokemon>>> = flow {
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
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun pokemonInfo(name: String) = flow {
        //emit(Loading)
        val res = safeApiCall(Dispatchers.IO) { pokemonApi.fetchPokemonInfo(name) }
        emit(res)
    }
}

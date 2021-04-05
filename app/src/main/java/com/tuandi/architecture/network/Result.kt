package com.tuandi.architecture.network

import com.tuandi.architecture.example.network.models.ErrorResponse

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Failure(val errorMessage: String? = null, val errorResponse: ErrorResponse? = null) :
        Result<Nothing>()
}

package com.tuandi.architecture.network

sealed class Outcome<T> {
    data class Success<T>(var data: T) : Outcome<T>()
    data class Failure<T>(val e: Throwable) : Outcome<T>()
    data class ApiError<T>(val e: Throwable) : Outcome<T>()

    companion object {
        fun <T> success(data: T): Outcome<T> =
            Success(data)

        fun <T> failure(e: Throwable): Outcome<T> =
            Failure(e)

        fun <T> apiError(e: Throwable): Outcome<T> =
            ApiError(e)
    }
}

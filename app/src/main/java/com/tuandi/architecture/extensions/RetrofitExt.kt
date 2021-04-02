package com.tuandi.architecture.extensions

import com.google.gson.Gson
import com.tuandi.architecture.example.network.models.ErrorResponse
import com.tuandi.architecture.network.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T : Any> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): Result<T> {
    return withContext(dispatcher) {
        try {
            Result.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException, is SocketTimeoutException -> Result.Error(errorMessage = "Network error")
                is HttpException -> {
                    val errorResponse = convertErrorBody(throwable)
                    Result.Error(errorResponse = errorResponse)
                }
                else -> {
                    Result.Error(errorMessage = "Error")
                }
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
    return try {
        throwable.response()?.errorBody()?.charStream()?.let {
            return Gson().fromJson(it, ErrorResponse::class.java)
        }
    } catch (exception: Exception) {
        null
    }
}

inline fun <T : Any> Result<T>.onError(
    crossinline onResult: Result.Error.() -> Unit
): Result<T> {
    if (this is Result.Error) {
        onResult(this)
    }
    return this
}

inline fun <T : Any> Result<T>.onSuccess(
    crossinline onResult: T.() -> Unit
): Result<T> {
    if (this is Result.Success) {
        onResult(this.data)
    }
    return this
}


package com.tuandi.architecture.extensions

import com.google.gson.Gson
import com.tuandi.architecture.example.network.models.ErrorResponse
import com.tuandi.architecture.helper.SuspensionFunction
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
            val res = apiCall.invoke()
            Result.Success(res)
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException, is SocketTimeoutException -> Result.Failure(errorMessage = throwable.message)
                is HttpException -> {
                    val errorResponse = convertErrorBody(throwable)
                    Result.Failure(errorResponse = errorResponse)
                }
                else -> {
                    Result.Failure(errorMessage = throwable.message)
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

@JvmSynthetic
@SuspensionFunction
suspend inline fun <T : Any> Result<T>.suspendOnSuccess(
    crossinline onResult: suspend T.() -> Unit
): Result<T> {
    if (this is Result.Success) {
        onResult(this.data)
    }
    return this
}


@JvmSynthetic
@SuspensionFunction
suspend inline fun <T : Any> Result<T>.suspendOnFailure(
    crossinline onResult: suspend Result.Failure.() -> Unit
): Result<T> {
    if (this is Result.Failure) {
        onResult(this)
    }
    return this
}

@JvmSynthetic
inline fun <T : Any> Result<T>.onFailure(
    onResult: Result.Failure.() -> Unit
): Result<T> {
    if (this is Result.Failure) {
        onResult(this)
    }
    return this
}

@JvmSynthetic
inline fun <T : Any> Result<T>.onSuccess(
    onResult: T.() -> Unit
): Result<T> {
    if (this is Result.Success) {
        onResult(this.data)
    }
    return this
}




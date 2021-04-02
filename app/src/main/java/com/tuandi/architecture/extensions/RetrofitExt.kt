package com.tuandi.architecture.extensions

import com.tuandi.architecture.network.Outcome
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

private fun <T> processError(error: Throwable): Outcome<T> {
    return when (error) {
        is HttpException -> {
            val response = error.response()!!
            val body = response.errorBody()!!
            Outcome.apiError(
                getError(
                    body,
                    error
                )
            )
        }
        is SocketTimeoutException, is IOException -> Outcome.failure(error)
        else -> Outcome.failure(error)
    }
}

private fun getError(
    responseBody: ResponseBody,
    throwable: Throwable
): Throwable {
    return try {
        val jsonObject = JSONObject(responseBody.string())
        Exception(jsonObject.getString("message"), throwable)
    } catch (e: Exception) {
        Exception(e.message ?: "$e")
    }
}

package com.algee.fetchexercise.data.repository.error

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import retrofit2.HttpException
import java.io.IOException

sealed class FetchApiError(cause: Throwable) : Throwable(cause) {

    /**
     * No Internet could cause this.
     */
    data class Connection(override val typedCause: IOException)
        : FetchApiError(typedCause), TypedCausedError<IOException>

    data class Http(override val typedCause: HttpException)
        : FetchApiError(typedCause), TypedCausedError<HttpException>

    data class Unknown(override val typedCause: Throwable)
        : FetchApiError(typedCause), TypedCausedError<Throwable>

    companion object {
        fun map(cause: Throwable): FetchApiError =
            when (cause) {
                is HttpException -> {
                    Http(cause)
                }
                is IOException -> {
                    Connection(cause)
                }
                else -> {
                    Unknown(cause)
                }
            }
    }
}


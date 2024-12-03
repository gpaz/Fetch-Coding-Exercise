package com.algee.fetchexercise.data.repository.error

import retrofit2.HttpException
import java.net.SocketTimeoutException

sealed class FetchApiError(cause: Throwable) : Throwable(cause) {
    data class Connection(override val typedCause: SocketTimeoutException)
        : FetchApiError(typedCause), TypedCausedError<SocketTimeoutException>

    data class Http(override val typedCause: HttpException)
        : FetchApiError(typedCause), TypedCausedError<HttpException>

    data class Unknown(override val typedCause: Exception)
        : FetchApiError(typedCause), TypedCausedError<Exception>
}
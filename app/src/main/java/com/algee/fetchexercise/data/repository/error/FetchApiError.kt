package com.algee.fetchexercise.data.repository.error

import retrofit2.HttpException
import java.io.IOException

sealed class FetchApiError(cause: Throwable) : Throwable(cause) {

    /**
     * "No Internet" would cause this.
     */
    data class Connection(override val typedCause: IOException)
        : FetchApiError(typedCause), TypedCausedError<IOException>

    /**
     * We successfully sent our request and we received an HTTP Request Result error code or
     * really [any non-2xx code][https://square.github.io/retrofit/2.x/retrofit/retrofit2/HttpException.html]
     */
    data class Http(override val typedCause: HttpException)
        : FetchApiError(typedCause), TypedCausedError<HttpException>

    /**
     * For any other unforeseen error code, we collect it into a generic container.
     * This is going to happen.  We should log these events to the cloud so as not
     * to miss out and adding these edge cases  to the code-base so that we can
     * handle them properly.
     */
    data class Unknown(override val typedCause: Throwable)
        : FetchApiError(typedCause), TypedCausedError<Throwable>

    companion object {
        /**
         * Maps an exception to its proper [FetchApiError] type.
         */
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


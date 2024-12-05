package com.algee.fetchexercise.data.repository

import com.algee.fetchexercise.data.repository.error.FetchApiError
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

internal object ApiHelper {
    internal inline fun <T> evaluateApiCall(block: () -> T ): Result<T, FetchApiError> =
        try {
            Ok(block())
        } catch (exception: Exception) {
            Err(FetchApiError.map(exception))
        }
}

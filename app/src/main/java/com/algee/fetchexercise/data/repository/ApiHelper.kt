package com.algee.fetchexercise.data.repository

import com.algee.fetchexercise.data.repository.error.FetchApiError
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

/**
 * Helper functions for the api.  Currently only one helper functions exist, but this would
 * be a space to add those types of functionsm extensions, etc.
 */
internal object ApiHelper {

    /**
     * Evaluates an api call, wrapping the call in a try-catch block, returning the result
     * if successful and mapping to a [FetchApiError] error result for any known and unexpected
     * failures during the api call.
     */
    internal inline fun <T> evaluateApiCall(block: () -> T ): Result<T, FetchApiError> =
        try {
            Ok(block())
        } catch (exception: Exception) {
            Err(FetchApiError.map(exception))
        }
}

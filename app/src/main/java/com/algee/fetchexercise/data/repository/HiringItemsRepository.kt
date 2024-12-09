package com.algee.fetchexercise.data.repository

import com.algee.fetchexercise.api.FetchApi
import com.algee.fetchexercise.api.model.HiringItem
import com.algee.fetchexercise.data.repository.ApiHelper.evaluateApiCall
import com.algee.fetchexercise.data.repository.error.FetchApiError
import com.github.michaelbull.result.Result

/**
 * Repository for hiring items.  This could be expanded to include a larger set of
 * api calls, at that point we can rename it.  This is essentially a proxy between
 * the domain use cases and the api, handling edge cases, thrown errors, and wrapping
 * everything into a nice common [Result] package that we can then process in the UI
 * logic.
 */
class HiringItemsRepository(
    private val fetchApi: FetchApi
) {

    /**
     * Returns the "Hiring" items returned by the hiring.json endpoint as-is.
     */
    suspend fun getHiringItems(): Result<List<HiringItem>, FetchApiError> =
        evaluateApiCall {
            fetchApi.getHiringItems()
        }
}

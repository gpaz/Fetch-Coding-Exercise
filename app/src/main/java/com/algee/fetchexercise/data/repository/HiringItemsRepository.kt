package com.algee.fetchexercise.data.repository

import com.algee.fetchexercise.api.FetchApi
import com.algee.fetchexercise.api.model.HiringItem
import com.algee.fetchexercise.data.repository.ApiHelper.evaluateApiCall
import com.algee.fetchexercise.data.repository.error.FetchApiError
import com.github.michaelbull.result.Result

class HiringItemsRepository(
    private val fetchApi: FetchApi
) {

    /**
     * Returns the "Hiring" items returned by the hiring.json endpoint
     */
    suspend fun getHiringItems(): Result<List<HiringItem>, FetchApiError> =
        evaluateApiCall {
            fetchApi.getHiringItems()
        }
}

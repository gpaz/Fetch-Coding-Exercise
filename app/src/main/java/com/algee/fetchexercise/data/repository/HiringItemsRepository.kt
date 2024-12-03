package com.algee.fetchexercise.data.repository

import com.algee.fetchexercise.api.FetchApi
import com.algee.fetchexercise.data.model.HiringItem
import com.algee.fetchexercise.data.repository.error.FetchApiError
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import retrofit2.HttpException
import java.net.SocketTimeoutException

class HiringItemsRepository(
    private val fetchApi: FetchApi
) {
    suspend fun getHiringItems(): Result<List<HiringItem>, FetchApiError> =
        try {
            Ok(fetchApi.getHiringItems())
        } catch (exception: Exception) {
            val fetchApiError = when (exception) {
                is HttpException -> {
                    FetchApiError.Http(exception)
                }

                is SocketTimeoutException -> {
                    FetchApiError.Connection(exception)
                }

                else -> {
                    FetchApiError.Unknown(exception)
                }
            }
            Err(fetchApiError)
        }
}

package com.algee.fetchexercise.api

import com.algee.fetchexercise.api.model.HiringItem
import retrofit2.HttpException
import retrofit2.http.GET
import kotlin.jvm.Throws

/**
 * Fetch api interfaces for Retrofit.
 */
interface FetchApi {

    /**
     * The only endpoint given for this coding exercise.
     *
     * Sample payload:
     *
     * [
     *   {"id": 755, "listId": 2, "name": ""},
     *   {"id": 203, "listId": 2, "name": ""},
     *   {"id": 684, "listId": 1, "name": "Item 684"},
     *   {"id": 276, "listId": 1, "name": "Item 276"},
     *   {"id": 736, "listId": 3, "name": null},
     *   {"id": 926, "listId": 4, "name": null},
     *   ....
     * ]
     */
    @Throws(HttpException::class)
    @GET("hiring.json")
    suspend fun getHiringItems(): List<HiringItem>
}

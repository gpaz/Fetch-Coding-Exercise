package com.algee.fetchexercise.api

import com.algee.fetchexercise.data.model.raw.HiringItemJson
import retrofit2.HttpException
import retrofit2.http.GET
import kotlin.jvm.Throws

interface FetchApi {

    @Throws(HttpException::class)
    @GET("hiring.json")
    suspend fun getHiringItems(): List<HiringItemJson>
}

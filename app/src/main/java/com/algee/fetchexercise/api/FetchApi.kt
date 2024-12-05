package com.algee.fetchexercise.api

import com.algee.fetchexercise.api.model.HiringItem
import retrofit2.HttpException
import retrofit2.http.GET
import kotlin.jvm.Throws

interface FetchApi {

    @Throws(HttpException::class)
    @GET("hiring.json")
    suspend fun getHiringItems(): List<HiringItem>
}

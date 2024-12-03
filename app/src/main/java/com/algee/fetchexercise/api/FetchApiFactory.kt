package com.algee.fetchexercise.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object FetchApiFactory {

    private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"

    operator fun invoke(
        baseUrl: String = BASE_URL,
        loggingLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BASIC
    ): FetchApi =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                level = loggingLevel
            }).build())
            .addConverterFactory(
                MoshiConverterFactory.create(FetchMoshi())
            )
            .build()
            .create(FetchApi::class.java)
}

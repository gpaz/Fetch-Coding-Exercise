package com.algee.fetchexercise.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class FetchApiFactory(
    private val mBaseUrl: String = BASE_URL,
    private val mLoggingLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BASIC
) {

    fun build() : FetchApi =
        Retrofit.Builder()
            .baseUrl(mBaseUrl)
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                level = mLoggingLevel
            }).build())
            .addConverterFactory(
                MoshiConverterFactory.create(FetchMoshi())
            )
            .build()
            .create(FetchApi::class.java)

    companion object {
        private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"
    }
}

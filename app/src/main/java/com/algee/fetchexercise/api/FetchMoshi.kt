package com.algee.fetchexercise.api

import com.algee.fetchexercise.api.serializers.HiringItemMoshiTypeAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object FetchMoshi {

    private val mMoshi: Moshi by lazy {
        Moshi.Builder()
            .add(HiringItemMoshiTypeAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    operator fun invoke(): Moshi = mMoshi
}

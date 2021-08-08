package com.zvonimirplivelic.stacksearch.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object StackSearchService {
    private val BASE_URL = "https://api.stackexchange.com/"

    val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(StackSearchApi::class.java)
}
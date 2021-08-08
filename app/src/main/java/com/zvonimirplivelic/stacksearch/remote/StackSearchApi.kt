package com.zvonimirplivelic.stacksearch.remote

import com.zvonimirplivelic.stacksearch.model.Question
import com.zvonimirplivelic.stacksearch.model.ResponseList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StackSearchApi {
    @GET("/2.3/questions?order=desc&sort=votes&tagged=android&site=stackoverflow")
    fun getQuestions(@Query("page")page: Int): Call<ResponseList<Question>>
}
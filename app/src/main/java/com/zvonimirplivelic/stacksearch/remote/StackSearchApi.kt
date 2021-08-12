package com.zvonimirplivelic.stacksearch.remote

import com.zvonimirplivelic.stacksearch.model.Answer
import com.zvonimirplivelic.stacksearch.model.Question
import com.zvonimirplivelic.stacksearch.model.ResponseList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StackSearchApi {

    @GET("/2.3/questions?order=desc&sort=creation&site=stackoverflow")
    fun getQuestions(): Call<ResponseList<Question>>

    @GET("/2.3/questions?order=desc&sort=votes&site=stackoverflow")
    fun getQuestionsQueried(
        @Query("tagged") searchString: String?
    ): Call<ResponseList<Question>>

    @GET("/2.3/questions/{id}/answers?&order=desc&sort=votes&site=stackoverflow")
    fun getAnswers(
        @Path("id") questionId: Int,
        @Query("page") page: Int
    ): Call<ResponseList<Answer>>
}
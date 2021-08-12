package com.zvonimirplivelic.stacksearch.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zvonimirplivelic.stacksearch.model.Question
import com.zvonimirplivelic.stacksearch.model.ResponseList
import com.zvonimirplivelic.stacksearch.remote.StackSearchService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "StackQuestionsViewModel"

class StackQuestionsViewModel : ViewModel() {
    val questionsResponse = MutableLiveData<List<Question>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String?>()

    fun getStackQuestions() {
        StackSearchService.api.getQuestions()
            .enqueue(object : Callback<ResponseList<Question>> {
                override fun onResponse(
                    call: Call<ResponseList<Question>>,
                    response: Response<ResponseList<Question>>
                ) {
                    if (response.isSuccessful) {
                        val questionList = response.body()
//                        Log.d(TAG, "Query call: Empty")
                        questionList?.let {
                            questionsResponse.value = questionList.items
                            loading.value = false
                            error.value = null
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseList<Question>>, t: Throwable) {
                    onError(t.localizedMessage)
                }
            })
    }

    fun getQueriedStackQuestions(queryString: String?) {
        StackSearchService.api.getQuestionsQueried(queryString)
            .enqueue(object : Callback<ResponseList<Question>> {
                override fun onResponse(
                    call: Call<ResponseList<Question>>,
                    response: Response<ResponseList<Question>>
                ) {
                    if (response.isSuccessful) {
                        val questionList = response.body()
                        Log.d(TAG, "Query call: $queryString")
                        questionList?.let {
                            questionsResponse.value = questionList.items
                            loading.value = false
                            error.value = null
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseList<Question>>, t: Throwable) {
                    onError(t.localizedMessage)
                }
            })
    }

    private fun onError(message: String) {
        error.value = message
        loading.value = false
    }
}
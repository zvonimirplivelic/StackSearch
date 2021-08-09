package com.zvonimirplivelic.stacksearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zvonimirplivelic.stacksearch.model.Question
import com.zvonimirplivelic.stacksearch.model.ResponseList
import com.zvonimirplivelic.stacksearch.remote.StackSearchService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StackQuestionsViewModel : ViewModel() {
    val questionsResponse = MutableLiveData<List<Question>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String?>()

    fun getQuestionsList() {
        StackSearchService.api.getQuestions(1)
            .enqueue(object : Callback<ResponseList<Question>> {
                override fun onResponse(
                    call: Call<ResponseList<Question>>,
                    response: Response<ResponseList<Question>>
                ) {
                    if (response.isSuccessful) {
                        val questionList = response.body()
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
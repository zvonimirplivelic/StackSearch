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

    private var page = 0

    fun getNextPage() {
        page++
        getQuestionsList()
    }

    fun getFirstPage() {
        page = 1
        getQuestionsList()
    }

    private fun getQuestionsList() {
        StackSearchService.api.getQuestions(page)
            .enqueue(object : Callback<ResponseList<Question>> {
                override fun onResponse(
                    call: Call<ResponseList<Question>>,
                    response: Response<ResponseList<Question>>
                ) {
                    if (response.isSuccessful) {
                        val questionList = response.body()
                        Log.d(TAG, "questList: $questionList")
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

        Log.d(TAG, "Page: $page")
    }

    private fun onError(message: String) {
        error.value = message
        loading.value = false
    }
}
package com.zvonimirplivelic.stacksearch.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zvonimirplivelic.stacksearch.model.Answer
import com.zvonimirplivelic.stacksearch.model.ResponseList
import com.zvonimirplivelic.stacksearch.remote.StackSearchService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "QuestionDetailViewModel"
class QuestionDetailViewModel : ViewModel() {
    val answerListResponse = MutableLiveData<List<Answer>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String?>()

    private var questionId = 0

    fun getAnswers(questionId: Int?) {
        questionId.let { questionId ->

            this.questionId = questionId!!
            StackSearchService.api.getAnswers(questionId)
                .enqueue(object : Callback<ResponseList<Answer>> {
                    override fun onResponse(
                        call: Call<ResponseList<Answer>>,
                        response: Response<ResponseList<Answer>>
                    ) {
                        val answers = response.body()
                        Log.d(TAG, "onResponse: $answers")
                        answers?.let {
                            answerListResponse.value = it.items
                            loading.value = false
                            error.value = null
                        }
                    }

                    override fun onFailure(call: Call<ResponseList<Answer>>, t: Throwable) {
                        onError(t.localizedMessage)
                    }
                })
        }
    }

    private fun onError(message: String) {
        error.value = message
        loading.value = false
    }
}
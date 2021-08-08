package com.zvonimirplivelic.stacksearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zvonimirplivelic.stacksearch.model.Question

class StackQuestionsViewModel: ViewModel() {
    val questionsResponse = MutableLiveData<List<Question>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun getQuestionsList() {

    }

    private fun onError(message:String) {
        error.value = message
        loading.value = false
    }
}
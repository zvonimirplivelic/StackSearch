package com.zvonimirplivelic.stacksearch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.zvonimirplivelic.stacksearch.R
import com.zvonimirplivelic.stacksearch.viewmodel.StackQuestionsViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val questionsAdapter = QuestionsAdapter(arrayListOf())
    private val viewModel: StackQuestionsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_questions.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = questionsAdapter
        }

        observeViewModel()

        viewModel.getQuestionsList()
    }

    private fun observeViewModel() {
        viewModel.questionsResponse.observe(this, { items ->
            items?.let {
                rv_questions.visibility = View.VISIBLE
                questionsAdapter.addQuestions(it)
            }
        })

        viewModel.error.observe(this, { errorMessage ->
            tv_error_message.visibility =
                if (errorMessage == null) View.GONE else View.VISIBLE

            tv_error_message.text = "Error\n$errorMessage"
        })

        viewModel.loading.observe( this, { isLoading ->
            isLoading?.let {
                progress_bar.visibility = if (it) View.VISIBLE else View.GONE

                if (it) {
                    tv_error_message.visibility = View.GONE
                    rv_questions.visibility = View.GONE
                }
            }
        })
    }
}
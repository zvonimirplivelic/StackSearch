package com.zvonimirplivelic.stacksearch.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.zvonimirplivelic.stacksearch.R
import com.zvonimirplivelic.stacksearch.model.Question
import com.zvonimirplivelic.stacksearch.viewmodel.StackQuestionsViewModel
import kotlinx.android.synthetic.main.activity_question_list.*
import kotlinx.coroutines.*

private const val TAG = "QuestionListActivity"

class QuestionListActivity : AppCompatActivity(), ListItemClickListener {

    private val questionsAdapter = QuestionsAdapter(arrayListOf(), this)
    private val viewModel: StackQuestionsViewModel by viewModels()
    private val linearLayoutManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_list)
        observeViewModel()

        rv_questions.apply {
            layoutManager = linearLayoutManager
            adapter = questionsAdapter
        }

        viewModel.getStackQuestions()

        swipe_layout.setOnRefreshListener {
            viewModel.getStackQuestions()
            progress_bar.visibility = View.VISIBLE
            rv_questions.visibility = View.GONE
        }
    }

    private fun observeViewModel() {

        viewModel.questionsResponse.observe(this, { items ->
            items?.let {
                rv_questions.visibility = View.VISIBLE
                swipe_layout.isRefreshing = false
                questionsAdapter.submitList(it)
            }
        })

        viewModel.error.observe(this, { errorMessage ->
            tv_error_message.visibility =
                if (errorMessage == null) View.GONE else View.VISIBLE
            tv_error_message.text = errorMessage
        })

        viewModel.loading.observe(this, { isLoading ->
            isLoading?.let {
                progress_bar.visibility = if (it) View.VISIBLE else View.GONE

                if (it) {
                    tv_error_message.visibility = View.GONE
                    rv_questions.visibility = View.GONE
                }
            }
        })
    }

    override fun <T> onListItemClicked(item: T) {
        startActivity(QuestionDetailActivity.getIntent(this, item as Question))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        val searchView = menu!!.findItem(R.id.menu_search_bar).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
            private var searchJob: Job? = null

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchJob?.cancel()
                searchJob = coroutineScope.launch {
                    newText?.let {
                        rv_questions.visibility = View.GONE
                        progress_bar.visibility = View.VISIBLE

                        delay(500L)

                        Log.d(TAG, "onQueryTextChange: $newText")

                        if (it.isNullOrEmpty()) {
                            viewModel.getStackQuestions()
                        } else {
                            viewModel.getQueriedStackQuestions(newText)
                        }

                    }
                }
                return false
            }
        })

        return true
    }
}


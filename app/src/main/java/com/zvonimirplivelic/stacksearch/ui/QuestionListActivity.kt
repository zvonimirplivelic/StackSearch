package com.zvonimirplivelic.stacksearch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zvonimirplivelic.stacksearch.R
import com.zvonimirplivelic.stacksearch.model.Question
import com.zvonimirplivelic.stacksearch.viewmodel.StackQuestionsViewModel
import kotlinx.android.synthetic.main.activity_main.*

class QuestionListActivity : AppCompatActivity(), ListItemClickListener {

    private val questionsAdapter = QuestionsAdapter(arrayListOf(), this)
    private val viewModel: StackQuestionsViewModel by viewModels()
    private val linearLayoutManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_questions.apply {
            layoutManager = linearLayoutManager
            adapter = questionsAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        val childCount = questionsAdapter.itemCount
                        val lastPosition =
                            linearLayoutManager.findLastCompletelyVisibleItemPosition()

                        if (childCount - 1 == lastPosition && progress_bar.visibility == View.GONE) {
                            progress_bar.visibility = View.VISIBLE
                            viewModel.getNextPage()
                        }
                    }
                }
            })
        }

        observeViewModel()

        viewModel.getNextPage()

        swipe_layout.setOnRefreshListener {
            questionsAdapter.clearQuestions()
            viewModel.getFirstPage()
            progress_bar.visibility = View.VISIBLE
            rv_questions.visibility = View.GONE
        }
    }

    private fun observeViewModel() {
        viewModel.questionsResponse.observe(this, { items ->
            items?.let {
                rv_questions.visibility = View.VISIBLE
                swipe_layout.isRefreshing = false
                questionsAdapter.addQuestions(it)
            }
        })

        viewModel.error.observe(this, { errorMessage ->
            tv_error_message.visibility =
                if (errorMessage == null) View.GONE else View.VISIBLE
            tv_error_message.text = resources.getText(R.string.error_message)
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
}
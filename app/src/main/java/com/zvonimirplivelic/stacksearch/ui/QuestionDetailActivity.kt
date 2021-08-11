package com.zvonimirplivelic.stacksearch.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zvonimirplivelic.stacksearch.R
import com.zvonimirplivelic.stacksearch.model.Answer
import com.zvonimirplivelic.stacksearch.model.Question
import com.zvonimirplivelic.stacksearch.model.convertTitle
import com.zvonimirplivelic.stacksearch.model.getDate
import com.zvonimirplivelic.stacksearch.ui.adapter.AnswerListAdapter
import com.zvonimirplivelic.stacksearch.viewmodel.QuestionDetailViewModel
import kotlinx.android.synthetic.main.activity_question_detail.*

class QuestionDetailActivity : AppCompatActivity(), ListItemClickListener {

    companion object {
        const val PARAM_QUESTION = "param_question"

        fun getIntent(context: Context, question: Question) =
            Intent(context, QuestionDetailActivity::class.java)
                .putExtra(PARAM_QUESTION, question)
    }

    var question: Question? = null
    private val viewModel: QuestionDetailViewModel by viewModels()
    private val answersAdapter = AnswerListAdapter(arrayListOf(), this)
    private val lm = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_detail)

        question = intent.extras?.getParcelable(PARAM_QUESTION)

        if (question == null) {
            Toast.makeText(
                this,
                "Can't open requested question",
                Toast.LENGTH_LONG
            ).show()

            finish()
        }

        populateUI()

        rv_answers_detail.apply {
            layoutManager = lm
            adapter = answersAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        val childCount = answersAdapter.itemCount
                        val lastPosition = lm.findLastCompletelyVisibleItemPosition()
                        if (childCount - 1 == lastPosition && progress_bar_detail.visibility == View.GONE) {
                            progress_bar_detail.visibility = View.VISIBLE
                            viewModel.getNextPage(question!!.questionId)
                        }
                    }
                }
            })
        }

        btn_open_question.setOnClickListener {
            val uri = "https://stackoverflow.com/questions/${question!!.questionId}"
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(browserIntent)
        }

        observeViewModel()

        viewModel.getNextPage(question!!.questionId)
    }

    private fun observeViewModel() {
        viewModel.answerListResponse.observe(this, { items ->
            items?.let {
                rv_answers_detail.visibility = View.VISIBLE
                answersAdapter.addAnswers(it)
            }
        })

        viewModel.error.observe(this, { errorMessage ->
            tv_error_message_detail.visibility =
                if (errorMessage == null) View.GONE else View.VISIBLE
            tv_error_message_detail.text = errorMessage
        })

        viewModel.loading.observe(this, { isLoading ->
            isLoading?.let {
                progress_bar_detail.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    tv_error_message_detail.visibility = View.GONE
                    rv_answers_detail.visibility = View.GONE
                }
            }
        })
    }

    private fun populateUI() {
        question_title_detail.text = convertTitle(question!!.title)
        question_owner_detail.text = question!!.owner.displayName
        question_score_detail.text = question!!.score
        question_date_detail.text = getDate(question!!.creationDate)
    }

    override fun <T> onListItemClicked(item: T) {
        val answerId = (item as Answer).answerId
        val uri = "https://stackoverflow.com/questions/${answerId}"
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(browserIntent)
    }
}
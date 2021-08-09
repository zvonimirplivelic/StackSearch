package com.zvonimirplivelic.stacksearch.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.zvonimirplivelic.stacksearch.R
import com.zvonimirplivelic.stacksearch.model.Question

class QuestionDetailActivity : AppCompatActivity() {

    companion object {
        const val PARAM_QUESTION = "param_question"

        fun getIntent(context: Context, question: Question) =
            Intent(context, QuestionDetailActivity::class.java)
                .putExtra(PARAM_QUESTION, question)
    }

    var question: Question? = null

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
    }
}
package com.zvonimirplivelic.stacksearch.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zvonimirplivelic.stacksearch.R
import com.zvonimirplivelic.stacksearch.model.Question
import com.zvonimirplivelic.stacksearch.model.convertTitle
import kotlinx.android.synthetic.main.question_item.view.*

class QuestionsAdapter(private val questionList: ArrayList<Question>) :
    RecyclerView.Adapter<QuestionsAdapter.AdapterViewHolder>() {

    fun addQuestions(newQuestions: List<Question>) {
        val currentLength = questionList.size
        questionList.addAll(newQuestions)
        notifyItemInserted(currentLength)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.question_item, parent, false)
        )

    override fun getItemCount() = questionList.size

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        holder.bind(questionList[position])
    }

    class AdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.item_title!!
        fun bind(question: Question) {
            title.text = convertTitle(question.title)
        }
    }

}
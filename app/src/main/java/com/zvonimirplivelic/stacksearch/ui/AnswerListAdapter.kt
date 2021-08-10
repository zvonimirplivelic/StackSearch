package com.zvonimirplivelic.stacksearch.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zvonimirplivelic.stacksearch.R
import com.zvonimirplivelic.stacksearch.model.Answer
import kotlinx.android.synthetic.main.answer_list_item.view.*

class AnswerListAdapter(private val answerList: ArrayList<Answer>) :
    RecyclerView.Adapter<AnswerListAdapter.AnswerAdapterViewHolder>() {

    fun addAnswers(newAnswers: List<Answer>) {
        val currentLength = answerList.size
        answerList.addAll(newAnswers)
        notifyItemInserted(currentLength)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = AnswerAdapterViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.answer_list_item, parent, false)
    )

    override fun onBindViewHolder(holder: AnswerAdapterViewHolder, position: Int) {
        holder.bind(answerList[position])
    }

    override fun getItemCount(): Int = answerList.size

    class AnswerAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.item_answer

        fun bind(answer: Answer) {
            title.text = answer.toString()
        }
    }
}
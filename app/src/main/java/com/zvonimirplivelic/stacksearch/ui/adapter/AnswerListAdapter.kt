package com.zvonimirplivelic.stacksearch.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zvonimirplivelic.stacksearch.R
import com.zvonimirplivelic.stacksearch.model.Answer
import com.zvonimirplivelic.stacksearch.model.Question
import com.zvonimirplivelic.stacksearch.ui.ListItemClickListener
import kotlinx.android.synthetic.main.answer_list_item.view.*

class AnswerListAdapter(
    private var answerList: List<Answer>,
    private val listener: ListItemClickListener
) :
    RecyclerView.Adapter<AnswerListAdapter.AnswerAdapterViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = AnswerAdapterViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.answer_list_item, parent, false),
        listener
    )

    override fun onBindViewHolder(holder: AnswerAdapterViewHolder, position: Int) {
        holder.bind(answerList[position])
    }

    override fun getItemCount(): Int = answerList.size


    fun submitAnswerList(newList: List<Answer>) {
        val oldList = answerList
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            AnswerListDiffCallback(
                oldList, newList
            )
        )
        answerList = newList as ArrayList<Answer>
        diffResult.dispatchUpdatesTo(this)
    }

    class AnswerListDiffCallback(
        var oldList: List<Answer>,
        var newList: List<Answer>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].answerId == newList[newItemPosition].answerId
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    class AnswerAdapterViewHolder(view: View, private val listener: ListItemClickListener) :
        RecyclerView.ViewHolder(view) {

        private val context: Context = view.context
        private val cardViewAnswer = view.card_view_answer
        private val answerDescription = view.item_answer

        fun bind(answer: Answer) {

            if (answer.isAccepted) {
                cardViewAnswer.setCardBackgroundColor(ContextCompat.getColor(context, R.color.stack_orange_dark))
                answerDescription.setTextColor(Color.BLACK)
            }

            answerDescription.text = answer.toString()

            cardViewAnswer.setOnClickListener { listener.onListItemClicked(answer) }
        }
    }
}
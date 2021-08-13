package com.zvonimirplivelic.stacksearch.ui.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zvonimirplivelic.stacksearch.R
import com.zvonimirplivelic.stacksearch.model.Question
import com.zvonimirplivelic.stacksearch.model.convertTitle
import com.zvonimirplivelic.stacksearch.model.getDate
import com.zvonimirplivelic.stacksearch.ui.ListItemClickListener
import kotlinx.android.synthetic.main.question_list_item.view.*

class QuestionsAdapter(
    private var questionList: List<Question>,
    private val listener: ListItemClickListener
) : RecyclerView.Adapter<QuestionsAdapter.QuestionAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        QuestionAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.question_list_item, parent, false),
            listener
        )

    override fun getItemCount() = questionList.size

    override fun onBindViewHolder(holder: QuestionAdapterViewHolder, position: Int) {
        holder.bind(questionList[position])
    }

    fun submitQuestionList(newList: List<Question>) {
        val oldList = questionList
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            QuestionListDiffCallback(
                oldList, newList
            )
        )
        questionList = newList as ArrayList<Question>
        diffResult.dispatchUpdatesTo(this)
    }

    class QuestionListDiffCallback(
        var oldList: List<Question>,
        var newList: List<Question>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].questionId == newList[newItemPosition].questionId
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    class QuestionAdapterViewHolder(view: View, private val listener: ListItemClickListener) :
        RecyclerView.ViewHolder(view) {
        private val cardViewQuestion = view.card_view_question
        private val resources: Resources = view.resources

        private val title = itemView.tv_item_title
        private val score = itemView.tv_item_score
        private val date = itemView.tv_item_date
        private val ownerName = itemView.tv_item_owner_name
        private val answerCount = itemView.tv_item_answer_count

        fun bind(question: Question) {

            title.text = convertTitle(question.title)
            date.text = getDate(question.creationDate)

            score.text = String.format(
                resources.getString(R.string.tv_score_text),
                question.score
            )

            ownerName.text = String.format(
                resources.getString(R.string.tv_item_owner_name_text),
                question.owner.displayName
            )

            answerCount.text = String.format(
                resources.getString(R.string.tv_item_answer_count),
                question.answerCount
            )

            cardViewQuestion.setOnClickListener { listener.onListItemClicked(question) }
        }
    }
}
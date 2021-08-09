package com.zvonimirplivelic.stacksearch.ui

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zvonimirplivelic.stacksearch.R
import com.zvonimirplivelic.stacksearch.model.Question
import com.zvonimirplivelic.stacksearch.model.convertTitle
import com.zvonimirplivelic.stacksearch.model.getDate
import kotlinx.android.synthetic.main.question_item.view.*

class QuestionsAdapter(private val questionList: ArrayList<Question>) :
    RecyclerView.Adapter<QuestionsAdapter.QuestionAdapterViewHolder>() {

    fun addQuestions(newQuestions: List<Question>) {
        val currentLength = questionList.size
        questionList.addAll(newQuestions)
        notifyItemInserted(currentLength)
    }

    fun clearQuestions() {
        questionList.clear()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        QuestionAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.question_item, parent, false)
        )

    override fun getItemCount() = questionList.size

    override fun onBindViewHolder(holder: QuestionAdapterViewHolder, position: Int) {
        holder.bind(questionList[position])
    }

    class QuestionAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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
        }
    }

}
package com.zvonimirplivelic.stacksearch.model

import android.os.Build
import android.text.Html
import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("accepted_answer_id")
    val acceptedAnswerId: Int,
    @SerializedName("answer_count")
    val answerCount: Int,
    @SerializedName("content_license")
    val contentLicense: String,
    @SerializedName("creation_date")
    val creationDate: Int,
    @SerializedName("is_answered")
    val isAnswered: Boolean,
    @SerializedName("last_activity_date")
    val lastActivityDate: Int,
    @SerializedName("last_edit_date")
    val lastEditDate: Int,
    @SerializedName("link")
    val link: String,
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("protected_date")
    val protectedDate: Int,
    @SerializedName("question_id")
    val questionId: Int,
    @SerializedName("score")
    val score: Int,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("title")
    val title: String,
    @SerializedName("view_count")
    val viewCount: Int
)

fun convertTitle(title: String?) =
    if(Build.VERSION.SDK_INT >= 24) {
        Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        Html.fromHtml(title).toString()
    }
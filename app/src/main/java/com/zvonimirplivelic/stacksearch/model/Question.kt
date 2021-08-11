package com.zvonimirplivelic.stacksearch.model

import android.os.Build
import android.os.Parcelable
import android.text.Html
import android.text.format.DateFormat
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Question(
    @SerializedName("accepted_answer_id")
    val acceptedAnswerId: Int,
    @SerializedName("answer_count")
    val answerCount: String?,
    @SerializedName("content_license")
    val contentLicense: String?,
    @SerializedName("creation_date")
    val creationDate: Long,
    @SerializedName("is_answered")
    val isAnswered: Boolean,
    @SerializedName("last_activity_date")
    val lastActivityDate: Int,
    @SerializedName("last_edit_date")
    val lastEditDate: Int,
    @SerializedName("link")
    val link: String?,
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("protected_date")
    val protectedDate: Int,
    @SerializedName("question_id")
    val questionId: Int,
    @SerializedName("score")
    val score: String?,
    @SerializedName("tags")
    val tags: ArrayList<String>?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("view_count")
    val viewCount: Int
) : Parcelable

fun convertTitle(title: String?) =
    if (Build.VERSION.SDK_INT >= 24) {
        Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        Html.fromHtml(title).toString()
    }

fun getDate(timestamp: Long?): String {
    var time = ""
    timestamp?.let {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp * 1000
        time = DateFormat.format("dd-MM-yyyy hh:mm:ss", calendar).toString()
    }
    return time
}


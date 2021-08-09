package com.zvonimirplivelic.stacksearch.model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.text.Html
import android.text.format.DateFormat
import com.google.gson.annotations.SerializedName
import java.util.*

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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        TODO("owner"),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(acceptedAnswerId)
        parcel.writeString(answerCount)
        parcel.writeString(contentLicense)
        parcel.writeLong(creationDate)
        parcel.writeByte(if (isAnswered) 1 else 0)
        parcel.writeInt(lastActivityDate)
        parcel.writeInt(lastEditDate)
        parcel.writeString(link)
        parcel.writeInt(protectedDate)
        parcel.writeInt(questionId)
        parcel.writeString(score)
        parcel.writeStringList(tags)
        parcel.writeString(title)
        parcel.writeInt(viewCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }
}

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


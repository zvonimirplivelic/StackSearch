package com.zvonimirplivelic.stacksearch.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Answer(
    @SerializedName("answer_id")
    val answerId: Int,
    @SerializedName("community_owned_date")
    val communityOwnedDate: Int,
    @SerializedName("content_license")
    val contentLicense: String?,
    @SerializedName("creation_date")
    val creationDate: Long,
    @SerializedName("is_accepted")
    val isAccepted: Boolean,
    @SerializedName("last_activity_date")
    val lastActivityDate: Int,
    @SerializedName("last_edit_date")
    val lastEditDate: Int,
    @SerializedName("owner")
    val owner: Owner?,
    @SerializedName("question_id")
    val questionId: Int,
    @SerializedName("score")
    val score: Int
) : Parcelable {

    override fun toString(): String =
        "Score: $score - Date answered: ${getDate(creationDate)}"

}
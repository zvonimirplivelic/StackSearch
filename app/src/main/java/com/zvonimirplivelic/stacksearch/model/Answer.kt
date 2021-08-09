package com.zvonimirplivelic.stacksearch.model


import com.google.gson.annotations.SerializedName

data class Answer(
    @SerializedName("answer_id")
    val answerId: Int,
    @SerializedName("community_owned_date")
    val communityOwnedDate: Int,
    @SerializedName("content_license")
    val contentLicense: String,
    @SerializedName("creation_date")
    val creationDate: Long,
    @SerializedName("is_accepted")
    val isAccepted: Boolean,
    @SerializedName("last_activity_date")
    val lastActivityDate: Int,
    @SerializedName("last_edit_date")
    val lastEditDate: Int,
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("question_id")
    val questionId: Int,
    @SerializedName("score")
    val score: Int
) {
    override fun toString(): String =
        "$answerId - $score - ${getDate(creationDate)} - ${if(isAccepted) "ACCEPTED" else "NOT ACCEPTED"}"
}
package com.zvonimirplivelic.stacksearch.model
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Owner(
    @SerializedName("accept_rate")
    val acceptRate: Int,
    @SerializedName("account_id")
    val accountId: Int,
    @SerializedName("display_name")
    val displayName: String?,
    @SerializedName("link")
    val link: String?,
    @SerializedName("profile_image")
    val profileImage: String?,
    @SerializedName("reputation")
    val reputation: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("user_type")
    val userType: String?
) : Parcelable


package com.zvonimirplivelic.stacksearch.model
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(acceptRate)
        parcel.writeInt(accountId)
        parcel.writeString(displayName)
        parcel.writeString(link)
        parcel.writeString(profileImage)
        parcel.writeInt(reputation)
        parcel.writeInt(userId)
        parcel.writeString(userType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Owner> {
        override fun createFromParcel(parcel: Parcel): Owner {
            return Owner(parcel)
        }

        override fun newArray(size: Int): Array<Owner?> {
            return arrayOfNulls(size)
        }
    }
}


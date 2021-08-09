package com.zvonimirplivelic.stacksearch.model


import android.content.ClipData
import com.google.gson.annotations.SerializedName

data class ResponseList<T>(
    @SerializedName("items")
    val items: List<T>
)

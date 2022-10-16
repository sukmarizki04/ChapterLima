package com.example.challenge5.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResponse(
    @SerializedName("results")
    val movies : List<Data>
) : Parcelable {
    constructor() : this(mutableListOf())
}
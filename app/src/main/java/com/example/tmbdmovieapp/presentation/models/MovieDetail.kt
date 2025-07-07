package com.example.tmbdmovieapp.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class MovieDetail(
    val title: String,
    val description: String,
    val posterUrl: String?,
    val releaseYear: String,
    val rating: Double,
    val favorite : Boolean = false
):Parcelable

package com.example.tmbdmovieapp.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object Favorites

@Serializable
data class Detail(
    val title: String,
    val description: String,
    val posterUrl: String?,
    val releaseYear: String,
    val rating: Double,
    val favorite : Boolean = false
)

@Serializable
object Search


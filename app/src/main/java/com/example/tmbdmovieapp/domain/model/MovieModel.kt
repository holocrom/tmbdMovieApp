package com.example.tmbdmovieapp.domain.model

data class MovieModel (
    val id: Int,
    val title: String,
    val description: String,
    val posterUrl: String?,
    val releaseYear: String,
    val rating: Double,
    val favorite : Boolean = false
)
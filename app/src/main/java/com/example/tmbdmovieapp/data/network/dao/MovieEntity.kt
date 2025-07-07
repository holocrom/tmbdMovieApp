package com.example.tmbdmovieapp.data.network.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity (
    @PrimaryKey
    val id:Int,
    val title:String,
    val description: String,
    val posterUrl: String?,
    val releaseYear: String,
    val rating: Double,
    val favorite : Boolean = false
)


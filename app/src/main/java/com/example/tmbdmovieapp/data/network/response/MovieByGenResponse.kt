package com.example.tmbdmovieapp.data.network.response

import com.google.gson.annotations.SerializedName

data class MovieByGenResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)

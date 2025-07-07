package com.example.tmbdmovieapp.data.network.response

import com.google.gson.annotations.SerializedName

data class MovieSearchResponse(
    @SerializedName("results")
    val results: List<MovieDto>
)


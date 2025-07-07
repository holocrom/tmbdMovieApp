package com.example.tmbdmovieapp.data.network

import com.example.tmbdmovieapp.data.network.response.MovieByGenResponse
import com.example.tmbdmovieapp.data.network.response.MovieSearchResponse
import com.example.tmbdmovieapp.data.network.response.PopularMovieResponse
import com.example.tmbdmovieapp.utils.AppConstants
import retrofit2.http.GET
import retrofit2.http.Query

interface TmbdApiService {

    @GET(AppConstants.POPULAR_MOVIE)
    suspend fun getPopularMovie(
        @Query("api_key") apiKey: String,
        @Query("page") page: String
    ): PopularMovieResponse

    @GET(AppConstants.SEARCH_MOVIE)
    suspend fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): MovieSearchResponse

    @GET(AppConstants.GEN_MOVIE)
    suspend fun getGenreMovie(
        @Query("api_key") apiKey: String
    ): MovieByGenResponse
}
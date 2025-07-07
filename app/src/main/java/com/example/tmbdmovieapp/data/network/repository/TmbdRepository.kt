package com.example.tmbdmovieapp.data.network.repository

import com.example.tmbdmovieapp.data.network.TmbdApiService
import com.example.tmbdmovieapp.data.network.response.MovieByGenResponse
import com.example.tmbdmovieapp.data.network.response.PopularMovieResponse
import com.example.tmbdmovieapp.utils.AppConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TmbdRepository @Inject constructor(private val tmbdApiService: TmbdApiService) {

    fun getPopularMovie(): Flow<PopularMovieResponse> = flow {
        val response = tmbdApiService.getPopularMovie(AppConstants.API_KEY,"1")
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun getMovieByName(title:String) = flow {
        val response = tmbdApiService.searchMovie(title, "1")
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun getGenreMovie(): Flow<MovieByGenResponse> = flow {
        val response = tmbdApiService.getGenreMovie(AppConstants.API_KEY)
        emit(response)
    }.flowOn(Dispatchers.IO)

}

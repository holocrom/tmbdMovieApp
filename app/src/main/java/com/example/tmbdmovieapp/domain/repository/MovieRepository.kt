package com.example.tmbdmovieapp.domain.repository

import com.example.tmbdmovieapp.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getPopularMovies(): Flow<List<MovieModel>>
    suspend fun getMovieByName(name:String): Flow<List<MovieModel>>
    suspend fun getMovieByGen(name:String): Flow<List<MovieModel>>
}
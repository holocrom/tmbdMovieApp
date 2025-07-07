package com.example.tmbdmovieapp.data.network.repository

import com.example.tmbdmovieapp.data.network.TmbdApiService
import com.example.tmbdmovieapp.domain.model.MovieModel
import com.example.tmbdmovieapp.domain.repository.MovieRepository
import com.example.tmbdmovieapp.utils.AppConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: TmbdApiService,
    private val mapper: MovieMapper
) : MovieRepository {

    override suspend fun getPopularMovies(): Flow<List<MovieModel>> = flow {
        try {
            val response = apiService.getPopularMovie(AppConstants.API_KEY, "1")
            emit(response.results.map { mapper.mapFromDto(it) })
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getMovieByName(name:String): Flow<List<MovieModel>> = flow {
        try {
            val response = apiService.searchMovie(AppConstants.API_KEY, name,1)
            emit(response.results.map { mapper.mapFromDto(it) })
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getMovieByGen(name: String): Flow<List<MovieModel>> {
        TODO("Not yet implemented")
    }
}


package com.example.tmbdmovieapp.domain.useCases

import com.example.tmbdmovieapp.domain.model.MovieModel
import com.example.tmbdmovieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(): Flow<List<MovieModel>> = repository.getPopularMovies()
}
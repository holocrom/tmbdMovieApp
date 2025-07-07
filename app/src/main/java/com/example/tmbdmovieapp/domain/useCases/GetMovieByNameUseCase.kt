package com.example.tmbdmovieapp.domain.useCases

import com.example.tmbdmovieapp.domain.model.MovieModel
import com.example.tmbdmovieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieByNameUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(name:String): Flow<List<MovieModel>> = repository.getMovieByName(name)
}
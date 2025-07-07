package com.example.tmbdmovieapp.domain.useCases

import com.example.tmbdmovieapp.data.network.repository.MovieRoomRepository
import com.example.tmbdmovieapp.domain.model.MovieModel
import javax.inject.Inject

class UpdateMovieUseCase @Inject constructor(private val movieRoomRepository: MovieRoomRepository){

    suspend operator fun invoke(movieModel: MovieModel){
        movieRoomRepository.update(movieModel)
    }

}
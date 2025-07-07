package com.example.tmbdmovieapp.data.network.repository

import com.example.tmbdmovieapp.data.network.response.MovieDto
import com.example.tmbdmovieapp.domain.model.MovieModel

class MovieMapper {
    fun mapFromDto(dto: MovieDto): MovieModel {
        return MovieModel(
            id = dto.id,
            title = dto.title,
            description = dto.overview,
            posterUrl = "https://image.tmdb.org/t/p/w500${dto.posterPath}",
            releaseYear = dto.releaseDate.take(4),
            rating = dto.voteAverage
        )
    }
}
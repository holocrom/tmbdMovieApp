package com.example.tmbdmovieapp.data.network.repository

import com.example.tmbdmovieapp.data.network.dao.MovieDao
import com.example.tmbdmovieapp.data.network.dao.MovieEntity
import com.example.tmbdmovieapp.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRoomRepository @Inject constructor(private val movieDao: MovieDao){

    val film: Flow<List<MovieModel>> = movieDao.getMovie().map { items ->
        items.map {
            MovieModel(it.id, it.title, it.description, it.posterUrl, it.releaseYear, it.rating, it.favorite)
        }
    }

    fun addMovie(movieModel: MovieModel){
        movieDao.addMovie(movieModel.toData())
    }

    fun update(movieModel: MovieModel){
        movieDao.updateMovie(movieModel.toData())
    }

    fun delete(movieModel: MovieModel){
        movieDao.deleteMovie(movieModel.toData())
    }

}

fun MovieModel.toData():MovieEntity{
    return MovieEntity(this.id, this.title, this.description, this.posterUrl, this.releaseYear, this.rating, this.favorite)
}

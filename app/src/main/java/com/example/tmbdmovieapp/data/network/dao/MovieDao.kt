package com.example.tmbdmovieapp.data.network.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * from MovieEntity")
    fun getMovie(): Flow<List<MovieEntity>>

    @Insert
    fun addMovie(item:MovieEntity)

    @Update
    fun updateMovie(item:MovieEntity)

    @Delete
    fun deleteMovie(item: MovieEntity)
}
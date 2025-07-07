package com.example.tmbdmovieapp.data.network.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tmbdmovieapp.data.network.dao.MovieDao
import com.example.tmbdmovieapp.data.network.dao.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class TmbdDataBase: RoomDatabase() {

    abstract fun movieDao(): MovieDao

}
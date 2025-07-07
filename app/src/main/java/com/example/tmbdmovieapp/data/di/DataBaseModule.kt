package com.example.tmbdmovieapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.tmbdmovieapp.data.network.dao.MovieDao
import com.example.tmbdmovieapp.data.network.repository.TmbdDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    fun providesMovieDao(tmbdDataBase: TmbdDataBase):MovieDao{
        return tmbdDataBase.movieDao()
    }

    @Provides
    @Singleton
    fun providesMovieDataBase(@ApplicationContext appContext: Context) : TmbdDataBase{
        return Room.databaseBuilder(appContext,TmbdDataBase::class.java,"TmbdDataBase").build()
    }
}
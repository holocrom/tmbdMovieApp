package com.example.tmbdmovieapp.data.di

import com.example.tmbdmovieapp.data.network.TmbdApiService
import com.example.tmbdmovieapp.data.network.repository.MovieMapper
import com.example.tmbdmovieapp.data.network.repository.MovieRepositoryImpl
import com.example.tmbdmovieapp.domain.repository.MovieRepository
import com.example.tmbdmovieapp.utils.AppConstants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideMovieApiService(retrofit: Retrofit): TmbdApiService {
        return retrofit.create(TmbdApiService::class.java)
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    //-------------------------------------------------------------
    @Provides
    @Singleton
    fun provideMovieMapper(): MovieMapper = MovieMapper()

    @Provides
    @Singleton
    fun provideMovieRepository(
        apiService: TmbdApiService,
        mapper: MovieMapper
    ): MovieRepository = MovieRepositoryImpl(apiService, mapper)
}
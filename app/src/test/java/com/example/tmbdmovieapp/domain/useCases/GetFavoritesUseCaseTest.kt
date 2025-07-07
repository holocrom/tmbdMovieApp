package com.example.tmbdmovieapp.domain.useCases

import com.example.tmbdmovieapp.data.network.repository.MovieRoomRepository
import com.example.tmbdmovieapp.domain.model.MovieModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetFavoritesUseCaseTest{

    @RelaxedMockK
    private lateinit var movieRoomRepository: MovieRoomRepository

    private lateinit var getFavoritesUseCase: GetFavoritesUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getFavoritesUseCase = GetFavoritesUseCase(movieRoomRepository)
    }


    @Test
    fun `debe retornar un flow de peliculas favoritas`() = runBlocking {
        // Given
        val testMovies = listOf(
            MovieModel(1, "Movie 1", "Desc 1", "url1", "2023", 8.5, true),
            MovieModel(2, "Movie 2", "Desc 2", "url2", "2023", 7.0, true)
        )

        coEvery { movieRoomRepository.film } returns flowOf(testMovies)

        // When
        val result = getFavoritesUseCase().first()

        // Then
        assertEquals(2, result.size)
        assertEquals("Movie 1", result[0].title)
        assertEquals(true, result[0].favorite)
    }

    @Test
    fun `invoke devuelve una lista vacia si no hay favoritos`() = runBlocking {
        // Given
        coEvery { movieRoomRepository.film } returns flowOf(emptyList())

        // When
        val result = getFavoritesUseCase().first()

        // Then
        assertEquals(0, result.size)
    }

    @Test(expected = RuntimeException::class)
    fun `invoke suelta una excep cuando el servidor falla`(): Unit = runBlocking {
        // Given
        val testException = RuntimeException("Database error")
        coEvery { movieRoomRepository.film } returns flow { throw testException }

        // When
        getFavoritesUseCase().first()

        // Then - Se espera que lance la excepción
    }
}
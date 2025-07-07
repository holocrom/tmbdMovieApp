package com.example.tmbdmovieapp.domain.useCases

import com.example.tmbdmovieapp.domain.model.MovieModel
import com.example.tmbdmovieapp.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class GetPopularMoviesUseCaseTest{

    @RelaxedMockK
    private lateinit var repository: MovieRepository
    private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    @Before
    fun setUp() {
        repository = mockk()
        getPopularMoviesUseCase = GetPopularMoviesUseCase(repository)
    }

    @Test
    fun `obtenemos peliculas del repositorio`() = runBlocking {
        // Given
        val testMovies = listOf(
            MovieModel(1, "Movie 1", "Desc 1", "url1", "2023", 8.5, false),
            MovieModel(2, "Movie 2", "Desc 2", "url2", "2023", 7.0, true)
        )
        coEvery { repository.getPopularMovies() } returns flowOf(testMovies)

        // When
        val result = getPopularMoviesUseCase().toList()

        // Then
        assertEquals(1, result.size)
        assertEquals(testMovies, result[0])
    }

    @Test
    fun `lista vacia si no hay peliculas`() = runBlocking {
        // Given
        coEvery { repository.getPopularMovies() } returns flowOf(emptyList())

        // When
        val result = getPopularMoviesUseCase().toList()

        // Then
        assertEquals(1, result.size)
        assertEquals(0, result[0].size)
    }
}
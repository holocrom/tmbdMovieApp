package com.example.tmbdmovieapp.domain.useCases

import com.example.tmbdmovieapp.data.network.repository.MovieRoomRepository
import com.example.tmbdmovieapp.domain.model.MovieModel
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class AddMovieUseCaseTest{

    @RelaxedMockK
    private lateinit var movieRoomRepository: MovieRoomRepository
    private lateinit var addMovieUseCase: AddMovieUseCase

    @Before
    fun setUp() {
        movieRoomRepository = mockk(relaxed = true)
        addMovieUseCase = AddMovieUseCase(movieRoomRepository)
    }

    @Test
    fun `invoke debe acceder al repositorio con los parametros correctos`() = runBlocking {
        // Given
        val testMovie = MovieModel(
            id = 1,
            title = "Inception",
            description = "Una película peculiar...",
            posterUrl = "url1",
            releaseYear = "2010",
            rating = 8.8,
            favorite = true
        )

        // When
        addMovieUseCase(testMovie)

        // Then
        coVerify(exactly = 1) {
            movieRoomRepository.addMovie(
                withArg { movie ->
                    assertEquals(testMovie.id, movie.id)
                    assertEquals(testMovie.title, movie.title)
                    assertEquals(testMovie.description, movie.description)
                    assertEquals(testMovie.posterUrl, movie.posterUrl)
                    assertEquals(testMovie.releaseYear, movie.releaseYear)
                    assertEquals(testMovie.rating, movie.rating, 0.01)
                    assertEquals(testMovie.favorite, movie.favorite)
                }
            )
        }
    }
}
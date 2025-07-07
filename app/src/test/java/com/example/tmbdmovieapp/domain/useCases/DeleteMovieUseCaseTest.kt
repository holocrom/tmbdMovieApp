package com.example.tmbdmovieapp.domain.useCases

import com.example.tmbdmovieapp.data.network.repository.MovieRoomRepository
import com.example.tmbdmovieapp.domain.model.MovieModel
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class DeleteMovieUseCaseTest{

    @RelaxedMockK
    private lateinit var movieRoomRepository: MovieRoomRepository
    private lateinit var deleteMovieUseCase: DeleteMovieUseCase

    @Before
    fun setUp() {
        movieRoomRepository = mockk(relaxed = true)
        deleteMovieUseCase = DeleteMovieUseCase(movieRoomRepository)
    }

    @Test
    fun `deberia borrar la pelicula correcta`() = runBlocking {
        // Given
        val testMovie = MovieModel(
            id = 1,
            title = "Inception",
            description = "A dream heist movie",
            posterUrl = "poster_url",
            releaseYear = "2010",
            rating = 8.8,
            favorite = true
        )

        // When
        deleteMovieUseCase(testMovie)

        // Then
        coVerify(exactly = 1) {
            movieRoomRepository.delete(
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

    @Test
    fun `llamamos una unica vez al repositorio`() = runBlocking {
        // Given
        val testMovie = MovieModel(2, "Interstellar", "", "", "", 9.0, true)

        // When
        deleteMovieUseCase(testMovie)

        // Then
        coVerify(exactly = 1) { movieRoomRepository.delete(any()) }
    }
}
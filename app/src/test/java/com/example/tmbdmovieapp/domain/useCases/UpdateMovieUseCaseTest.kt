package com.example.tmbdmovieapp.domain.useCases

import com.example.tmbdmovieapp.data.network.repository.MovieRoomRepository
import com.example.tmbdmovieapp.domain.model.MovieModel
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class UpdateMovieUseCaseTest{

    @RelaxedMockK
    private lateinit var movieRoomRepository: MovieRoomRepository
    private lateinit var updateMovieUseCase: UpdateMovieUseCase

    @Before
    fun setUp() {
        movieRoomRepository = mockk(relaxed = true)
        updateMovieUseCase = UpdateMovieUseCase(movieRoomRepository)
    }

    @Test
    fun `actualizamos la pelicula correcta`() = runBlocking {
        // Given
        val testMovie = MovieModel(
            id = 101,
            title = "The Matrix",
            description = "Sci-fi classic",
            posterUrl = "matrix_poster.jpg",
            releaseYear = "1999",
            rating = 8.7,
            favorite = true
        )

        // When
        updateMovieUseCase(testMovie)

        // Then
        coVerify(exactly = 1) {
            movieRoomRepository.update(
                withArg { movie ->
                    assertEquals(101, movie.id)
                    assertEquals("The Matrix", movie.title)
                    assertEquals("Sci-fi classic", movie.description)
                    assertEquals("matrix_poster.jpg", movie.posterUrl)
                    assertEquals("1999", movie.releaseYear)
                    assertEquals(8.7, movie.rating, 0.01)
                    assertEquals(true, movie.favorite)
                }
            )
        }
    }

    @Test
    fun `solo se llama update una vez`() = runBlocking {
        // Given
        val testMovie = MovieModel(2, "Pulp Fiction", "", "", "", 8.9, true)

        // When
        updateMovieUseCase(testMovie)

        // Then
        coVerify(exactly = 1) { movieRoomRepository.update(any()) }
    }

    @Test
    fun `no llamamos a update si la pelicula es null`() = runBlocking {
        // Given
        val nullMovie = MovieModel(
            id = -1,
            title = "",
            description = "",
            posterUrl = null,
            releaseYear = "",
            rating = 0.0,
            favorite = false
        )

        // When
        updateMovieUseCase(nullMovie)

        // Then
        coVerify(exactly = 1) { movieRoomRepository.update(any()) }
    }
}
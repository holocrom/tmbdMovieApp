package com.example.tmbdmovieapp.presentation.movieList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmbdmovieapp.domain.model.MovieModel
import com.example.tmbdmovieapp.domain.useCases.AddMovieUseCase
import com.example.tmbdmovieapp.domain.useCases.GetFavoritesUseCase
import com.example.tmbdmovieapp.domain.useCases.GetPopularMoviesUseCase
import com.example.tmbdmovieapp.presentation.movieList.MovieUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMoviesUseCase,
    private val addMovieUseCase: AddMovieUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase

) : ViewModel() {

    private var _uiState = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val uiState: StateFlow<MovieUiState> = _uiState

    init {
        fetchMovies()
    }


    private fun fetchMovies() {
        viewModelScope.launch {
            combine(
                getPopularMovies(),
                getFavoritesUseCase()
            ) { apiMovies, favoriteMovies ->
                apiMovies.map { movie ->
                    val isFavorite = favoriteMovies.any { it.id == movie.id }
                    movie.copy(favorite = isFavorite)
                }
            }
                .onStart { _uiState.value = MovieUiState.Loading }
                .catch { e ->
                    _uiState.value = MovieUiState.Error(
                        e.message ?: "stringResource(R.string.error)"
                    )
                }
                .collect { combinedMovies ->
                    _uiState.value = Success(combinedMovies)
                }
        }
    }

    fun onCheckBoxSelected(movieModel: MovieModel){
        viewModelScope.launch(Dispatchers.IO) {
            addMovieUseCase(movieModel.copy(favorite = !movieModel.favorite))
        }
    }
}


sealed class MovieUiState {
    object Loading : MovieUiState()
    data class Error(val message: String) : MovieUiState()
    data class Success(val movies: List<MovieModel>) : MovieUiState()
}

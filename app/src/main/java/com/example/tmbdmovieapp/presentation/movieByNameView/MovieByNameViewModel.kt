package com.example.tmbdmovieapp.presentation.movieByNameView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmbdmovieapp.domain.model.MovieModel
import com.example.tmbdmovieapp.domain.useCases.AddMovieUseCase
import com.example.tmbdmovieapp.domain.useCases.GetMovieByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieByNameViewModel @Inject constructor(
    private val searchMoviesUseCase: GetMovieByNameUseCase,
    private val addMovieUseCase: AddMovieUseCase,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _searchResults = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val searchResults: StateFlow<SearchUiState> = _searchResults

    private val _hasSearched = MutableStateFlow(false)
    val hasSearched: StateFlow<Boolean> = _hasSearched

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun searchMovies() {
        _hasSearched.value = true
        viewModelScope.launch {
            _searchResults.value = SearchUiState.Loading
            searchMoviesUseCase(_searchQuery.value)
                .collect { movies ->
                    _searchResults.value = if (movies.isEmpty()) {
                        SearchUiState.Empty
                    } else {
                        SearchUiState.Success(movies)
                    }
                }
        }
    }

    fun onCheckBoxSelected(movieModel: MovieModel){
        viewModelScope.launch(Dispatchers.IO) {
            addMovieUseCase(movieModel.copy(favorite = !movieModel.favorite))
        }
    }
}

sealed class SearchUiState {
    object Idle : SearchUiState()
    object Empty : SearchUiState()
    object Loading : SearchUiState()
    data class Success(val movies: List<MovieModel>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}
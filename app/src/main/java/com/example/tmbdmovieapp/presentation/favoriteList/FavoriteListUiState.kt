package com.example.tmbdmovieapp.presentation.favoriteList

import com.example.tmbdmovieapp.domain.model.MovieModel

sealed interface FavoriteListUiState {
    object Loading: FavoriteListUiState
    data class Error(val throwable: Throwable):FavoriteListUiState
    data class Success(val task:List<MovieModel>):FavoriteListUiState
}
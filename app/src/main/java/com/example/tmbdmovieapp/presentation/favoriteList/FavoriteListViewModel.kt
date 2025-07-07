package com.example.tmbdmovieapp.presentation.favoriteList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmbdmovieapp.domain.model.MovieModel
import com.example.tmbdmovieapp.domain.useCases.DeleteMovieUseCase
import com.example.tmbdmovieapp.domain.useCases.GetFavoritesUseCase
import com.example.tmbdmovieapp.presentation.favoriteList.FavoriteListUiState.Error
import com.example.tmbdmovieapp.presentation.favoriteList.FavoriteListUiState.Loading
import com.example.tmbdmovieapp.presentation.favoriteList.FavoriteListUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteListViewModel @Inject constructor(
    private val deleteMovieUseCase: DeleteMovieUseCase,
    getFavoritesUseCase: GetFavoritesUseCase
):ViewModel(){

    val uiState: StateFlow<FavoriteListUiState> = getFavoritesUseCase().map (::Success)
        .catch{ Error(it)}
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    fun onItemRemoved(movieModel: MovieModel) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteMovieUseCase(movieModel)
        }
    }
}
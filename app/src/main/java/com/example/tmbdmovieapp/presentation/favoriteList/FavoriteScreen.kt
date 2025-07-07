package com.example.tmbdmovieapp.presentation.favoriteList

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.example.tmbdmovieapp.R
import com.example.tmbdmovieapp.domain.model.MovieModel
import com.example.tmbdmovieapp.presentation.movieList.MovieInfo
import com.example.tmbdmovieapp.presentation.movieList.MoviePoster

@Composable
fun FavoriteScreen (
    favoriteListViewModel: FavoriteListViewModel= hiltViewModel(),
    onBack:() -> Unit,
    navigateToHome:() -> Unit,
    navigateToDetail:(
        title: String,
        description: String,
        posterUrl: String?,
        releaseYear: String,
        rating: Double,
        favorite : Boolean ) -> Unit,
){

    val lifeCycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<FavoriteListUiState>(
        initialValue = FavoriteListUiState.Loading,
        key1 = lifeCycle,
        key2 = favoriteListViewModel
    ){
        lifeCycle.repeatOnLifecycle(state = Lifecycle.State.STARTED){
            favoriteListViewModel.uiState.collect{ value = it}
        }
    }
    when(uiState){
        is FavoriteListUiState.Error -> TODO()
        FavoriteListUiState.Loading -> {
            CircularProgressIndicator()
        }
        is FavoriteListUiState.Success -> {
            Box(modifier = Modifier.fillMaxSize()){
                MovieList(
                    (uiState as FavoriteListUiState.Success).task,
                    favoriteListViewModel,
                    onBack = onBack,
                    navigateToHome = navigateToHome,
                    navigateToDetail = navigateToDetail)
            }
        }
    }
}

@Composable
fun MovieList(
    film: List<MovieModel>,
    favoriteListViewModel: FavoriteListViewModel,
    onBack:()->Unit,
    navigateToHome: () -> Unit,
    navigateToDetail:(
        title: String,
        description: String,
        posterUrl: String?,
        releaseYear: String,
        rating: Double,
        favorite : Boolean) -> Unit,
    ) {

    Column(
        modifier = Modifier
            .padding(start = 16.dp, top = 64.dp, end = 16.dp, bottom = 32.dp)
    )
    {
        LazyColumn(modifier = Modifier
            .padding(start = 16.dp, top = 30.dp, end = 16.dp)
            .weight(1f)) {
            items(film, key = { it.id }) { movie ->
                MovieItem(movie, favoriteListViewModel, onItemClick = { navigateToDetail(
                    movie.title,
                    movie.description,
                    movie.posterUrl,
                    movie.releaseYear,
                    movie.rating,
                    movie.favorite)})
            }
        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { onBack() },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    stringResource(R.string.goBack),
                    style = MaterialTheme.typography.labelLarge)
            }
            Button(
                onClick = { navigateToHome() },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    stringResource(R.string.home),
                    style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}


@Composable
fun MovieItem(
    film: MovieModel,
    favoriteListViewModel: FavoriteListViewModel,
    onItemClick:() -> Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp)
            .clickable {
                onItemClick()
            },
        border = BorderStroke(2.dp, Color.Black)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            MoviePoster(film.posterUrl, modifier = Modifier.weight(0.4f))
            MovieInfo(film, modifier = Modifier.weight(0.5f))
            Checkbox(
                film.favorite,
                onCheckedChange = { favoriteListViewModel.onItemRemoved(film) })
        }
    }
}

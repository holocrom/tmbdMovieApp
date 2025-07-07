package com.example.tmbdmovieapp.presentation.movieByNameView

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tmbdmovieapp.R
import com.example.tmbdmovieapp.domain.model.MovieModel
import com.example.tmbdmovieapp.presentation.movieList.CheckboxFavorito
import com.example.tmbdmovieapp.presentation.movieList.MovieInfo
import com.example.tmbdmovieapp.presentation.movieList.MoviePoster
import com.example.tmbdmovieapp.ui.theme.SelectedButton
import com.example.tmbdmovieapp.ui.theme.UnSelectedButton

@Composable
fun SearchScreen(
    viewModel: MovieByNameViewModel = hiltViewModel(),
    navigateToFavorites:() -> Unit,
    navigateToHome:() -> Unit,
    navigateToDetail:(
        title: String,
        description: String,
        posterUrl: String?,
        releaseYear: String,
        rating: Double,
        favorite : Boolean ) -> Unit,
) {

    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchState by viewModel.searchResults.collectAsState()
    val hasSearched by viewModel.hasSearched.collectAsState()

    var selectedButton by remember { mutableStateOf<Boolean>(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp))
    {
        SearchBar(
            query = searchQuery,
            onQueryChange = viewModel::onSearchQueryChanged,
            onSearch = viewModel::searchMovies,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = searchState) {
            SearchUiState.Idle -> {
                if (hasSearched) {
                    Text(stringResource(R.string.writeMovieName))
                }
            }
            is SearchUiState.Empty -> {
                if (hasSearched) {
                    Text("${stringResource(R.string.noResultsFound)}: $searchQuery")
                }
            }
            is SearchUiState.Loading -> {
                CircularProgressIndicator()
            }
            is SearchUiState.Success -> {
                MovieList(
                    movies = state.movies,
                    viewModel = viewModel,
                    navigateToFavorite = navigateToFavorites,
                    navigateToDetail = navigateToDetail,
                    navigateToHome = navigateToHome
                    )
            }
            is SearchUiState.Error -> {
                Text("${stringResource(R.string.error)}: $state.message")
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { navigateToFavorites() },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!selectedButton) UnSelectedButton else SelectedButton
                    )
                ) {
                    Text(
                        stringResource(R.string.favorites),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                Button(
                    onClick = { navigateToHome() },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!selectedButton) UnSelectedButton else SelectedButton
                    )
                ) {
                    Text(
                        stringResource(R.string.home),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

@Composable
private fun MovieList(
    movies: List<MovieModel>,
    viewModel: MovieByNameViewModel,
    navigateToFavorite:() -> Unit,
    navigateToDetail:(
        title: String,
        description: String,
        posterUrl: String?,
        releaseYear: String,
        rating: Double,
        favorite : Boolean) -> Unit,
    navigateToHome: () -> Unit
    ){

    var selectedButton by remember { mutableStateOf<Boolean>(false) }

    Column(
        modifier = Modifier
            .padding(start = 16.dp, top = 64.dp, end = 16.dp, bottom = 32.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(movies, key = { it.id }) { movie ->
                MovieItem(
                    movie,
                    viewModel,
                    onItemClick = {
                        navigateToDetail(
                            movie.title,
                            movie.description,
                            movie.posterUrl,
                            movie.releaseYear,
                            movie.rating,
                            movie.favorite
                        )
                    }
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { navigateToFavorite() },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!selectedButton) UnSelectedButton else SelectedButton
                    )
                ) {
                    Text(
                        stringResource(R.string.favorites),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                Button(
                    onClick = { navigateToHome() },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!selectedButton) UnSelectedButton else SelectedButton
                    )
                ) {
                    Text(
                        stringResource(R.string.home),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            label = { Text(stringResource(R.string.addMovieName)) },
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = onSearch,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(stringResource(R.string.toFind))
        }
    }
}

@Composable
fun MovieItem(
    movie: MovieModel,
    viewModel: MovieByNameViewModel,
    onItemClick:() -> Unit
    ) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp)
            .clickable {
                onItemClick()
            },
        border = BorderStroke(2.dp, Color.Black)
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){

            MoviePoster(movie.posterUrl, modifier = Modifier.weight(0.4f))
            MovieInfo(movie, modifier = Modifier.weight(0.5f))
            CheckboxFavorito(
                movie.favorite,
                onCheckedChange = {viewModel.onCheckBoxSelected(movieModel = movie)},
                modifier = Modifier.weight(0.1f))
        }
    }
}
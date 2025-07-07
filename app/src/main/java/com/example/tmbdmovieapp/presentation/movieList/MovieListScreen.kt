package com.example.tmbdmovieapp.presentation.movieList


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.tmbdmovieapp.R
import com.example.tmbdmovieapp.domain.model.MovieModel
import com.example.tmbdmovieapp.ui.theme.SelectedButton
import com.example.tmbdmovieapp.ui.theme.UnSelectedButton

@Composable
fun MovieListScreen(
    viewModel: MovieViewModel = hiltViewModel(),
    navigateToFavorites:() -> Unit,
    navigateToDetail:(
        title: String,
        description: String,
        posterUrl: String?,
        releaseYear: String,
        rating: Double,
        favorite : Boolean ) -> Unit,
    navigateToSearch: () -> Unit
)
{

    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is MovieUiState.Loading -> FullScreenLoader()
        is MovieUiState.Error -> {
            val errorState = uiState as MovieUiState.Error
            ErrorScreen(message = errorState.message)
        }
        is MovieUiState.Success -> {
            val successState = uiState as MovieUiState.Success
            MovieList(
                movies = successState.movies,
                viewModel = viewModel,
                navigateToFavorite = navigateToFavorites,
                navigateToDetail = navigateToDetail,
                navigateToSearch = navigateToSearch
            )
        }
    }
}

@Composable
private fun MovieList(
    movies: List<MovieModel>,
    viewModel: MovieViewModel,
    navigateToFavorite:() -> Unit,
    navigateToDetail:(
        title: String,
        description: String,
        posterUrl: String?,
        releaseYear: String,
        rating: Double,
        favorite : Boolean) -> Unit,
    navigateToSearch: () -> Unit
) {

    var selectedButton by remember { mutableStateOf<Boolean>(false) }

    Column(
        modifier = Modifier
            .padding(start = 16.dp, top = 64.dp, end = 16.dp, bottom = 32.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(movies, key = { it.id }) { movie ->
                MovieItem(movie, viewModel, onItemClick = { navigateToDetail(
                    movie.title,
                    movie.description,
                    movie.posterUrl,
                    movie.releaseYear,
                    movie.rating,
                    movie.favorite) })
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
                   onClick = { navigateToSearch() },
                   shape = RoundedCornerShape(8.dp),
                   colors = ButtonDefaults.buttonColors(
                       containerColor = if (!selectedButton) UnSelectedButton else SelectedButton
                   )
               ) {
                   Text(
                       stringResource(R.string.searchButton),
                       style = MaterialTheme.typography.labelLarge
                   )
               }
            }
        }
    }
}

@Composable
fun MovieItem(
    movie: MovieModel,
    viewModel: MovieViewModel,
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

@Composable
fun MovieInfo(movie: MovieModel, modifier: Modifier){
    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            movie.title,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier =
                Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center)
        Text(
            text = "⭐ ${"%.1f".format(movie.rating)}/10",
            modifier =
                Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center)
        Text(
            movie.releaseYear,
            modifier =
                Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center)
    }
}

@Composable
fun MoviePoster(posterUrl: String?, modifier: Modifier = Modifier) {
    AsyncImage(
        model = "https://image.tmdb.org/t/p/w500$posterUrl",
        contentDescription = "Movie poster",
        modifier = modifier
            .aspectRatio(2f / 3)
            .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop,
        error = painterResource(R.drawable.no_url_xml),
        placeholder = painterResource(R.drawable.no_url_xml)
    )
}

@Composable
fun FullScreenLoader() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(message: String, onRetry: (() -> Unit)? = null) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Error: $message",
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(16.dp))
            onRetry?.let {
                Button(onClick = it) {
                    Text(stringResource(R.string.reintentar))
                }
            }
        }
    }
}

@Composable
fun CheckboxFavorito(
    isFavorite: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Checkbox(
            checked = isFavorite,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant,
                checkmarkColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.size(24.dp)
        )
    }
}
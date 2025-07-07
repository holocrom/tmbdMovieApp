package com.example.tmbdmovieapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.tmbdmovieapp.presentation.detailView.DetailCard
import com.example.tmbdmovieapp.presentation.favoriteList.FavoriteScreen
import com.example.tmbdmovieapp.presentation.movieByNameView.SearchScreen
import com.example.tmbdmovieapp.presentation.movieList.MovieListScreen

@Composable
fun NavigationWrapper() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            MovieListScreen(
                navigateToFavorites = { navController.navigate(Favorites) },
                navigateToDetail = { title, description, posterUrl, releaseYear, rating, favorite ->
                    navController.navigate(Detail(title, description, posterUrl, releaseYear, rating, favorite))
                },
                navigateToSearch = {navController.navigate(Search)}
            )
        }

        composable<Favorites> {
            FavoriteScreen(
                onBack = { navController.popBackStack()},
                navigateToHome = {navController.navigate(Home)},
                navigateToDetail = { title, description, posterUrl, releaseYear, rating, favorite ->
                    navController.navigate(Detail(title, description, posterUrl, releaseYear, rating, favorite))
                }
            )
        }

        composable<Detail> { backStackEntry ->
            val detail = backStackEntry.toRoute<Detail>()
            DetailCard(
                title = detail.title,
                description = detail.description,
                posterUrl = detail.posterUrl,
                releaseYear = detail.releaseYear,
                rating = detail.rating,
                onBack = { navController.popBackStack() }
            )
        }

        composable<Search> {
            SearchScreen(
                navigateToFavorites = { navController.navigate(Favorites) },
                navigateToDetail = { title, description, posterUrl, releaseYear, rating, favorite ->
                    navController.navigate(Detail(title, description, posterUrl, releaseYear, rating, favorite))
                },
                navigateToHome = { navController.navigate(Home) }
            )
        }
    }
}

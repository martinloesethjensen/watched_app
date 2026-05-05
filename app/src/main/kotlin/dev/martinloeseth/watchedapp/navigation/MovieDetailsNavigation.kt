package dev.martinloeseth.watchedapp.navigation

import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.martinloeseth.watchedapp.presentation.moviedetails.MovieDetailsRoute
import dev.martinloeseth.watchedapp.presentation.ui.LocalAnimatedContentScope
import kotlinx.serialization.Serializable

@Serializable
data class Details(val movieId: Int)

fun NavController.navigateToDetails(movieId: Int) {
    navigate(Details(movieId))
}

fun NavGraphBuilder.detailsScreen(onBackClick: () -> Unit) {
    composable<Details> {
        CompositionLocalProvider(LocalAnimatedContentScope provides this) {
            MovieDetailsRoute(onBackClick = onBackClick)
        }
    }
}

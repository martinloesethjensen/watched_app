package dev.martinloeseth.watchedapp.navigation

import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.martinloeseth.watchedapp.domain.models.Movie
import dev.martinloeseth.watchedapp.presentation.moviedetails.MovieDetailsRoute
import dev.martinloeseth.watchedapp.presentation.ui.LocalAnimatedContentScope
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class Details(val movieJson: String)

fun NavController.navigateToDetails(movie: Movie) {
    navigate(Details(Json.encodeToString(movie)))
}

fun NavGraphBuilder.detailsScreen(onBackClick: () -> Unit) {
    composable<Details> {
        CompositionLocalProvider(LocalAnimatedContentScope provides this) {
            MovieDetailsRoute(onBackClick = onBackClick)
        }
    }
}

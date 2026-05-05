package dev.martinloeseth.watchedapp.navigation

import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import dev.martinloeseth.watchedapp.domain.models.Movie
import dev.martinloeseth.watchedapp.presentation.search.SearchRoute
import dev.martinloeseth.watchedapp.presentation.ui.LocalAnimatedContentScope
import kotlinx.serialization.Serializable

@Serializable
data object Search

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    navigate(Search, navOptions)
}

fun NavGraphBuilder.searchScreen(
    onBackClick: () -> Unit,
    onCardClick: (Movie) -> Unit,
) {
    composable<Search> {
        CompositionLocalProvider(LocalAnimatedContentScope provides this) {
            SearchRoute(onBackClick = onBackClick, onCardClick = onCardClick)
        }
    }
}

package dev.martinloeseth.watchedapp.navigation

import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import dev.martinloeseth.watchedapp.presentation.home.HomeRoute
import dev.martinloeseth.watchedapp.presentation.ui.LocalAnimatedContentScope
import kotlinx.serialization.Serializable

@Serializable
data object Home

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(Home, navOptions)
}

fun NavGraphBuilder.homeScreen(
    onSearchClick: () -> Unit,
    onCardClick: (Int) -> Unit,
    onSettingsClick: () -> Unit,
) {
    composable<Home> {
        CompositionLocalProvider(LocalAnimatedContentScope provides this) {
            HomeRoute(
                onSearchClick = onSearchClick,
                onCardClick = onCardClick,
                onSettingsClick = onSettingsClick,
            )
        }
    }
}

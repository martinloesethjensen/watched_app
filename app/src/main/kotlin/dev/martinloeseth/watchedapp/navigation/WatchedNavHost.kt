package dev.martinloeseth.watchedapp.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.martinloeseth.watchedapp.presentation.ui.LocalSharedTransitionScope

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun WatchedNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    SharedTransitionLayout {
        CompositionLocalProvider(LocalSharedTransitionScope provides this) {
            NavHost(
                navController = navController,
                startDestination = Home,
                modifier = modifier,
            ) {
                homeScreen(
                    onSearchClick = navController::navigateToSearch,
                    onCardClick = navController::navigateToDetails,
                    onSettingsClick = navController::navigateToSettings,
                )
                searchScreen(
                    onBackClick = navController::popBackStack,
                    onCardClick = navController::navigateToDetails,
                )
                detailsScreen(onBackClick = navController::popBackStack)
                settingsScreen(onBackClick = navController::popBackStack)
            }
        }
    }
}

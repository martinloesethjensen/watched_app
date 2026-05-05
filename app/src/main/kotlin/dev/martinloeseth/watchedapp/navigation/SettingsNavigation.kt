package dev.martinloeseth.watchedapp.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.martinloeseth.watchedapp.presentation.settings.SettingsRoute
import kotlinx.serialization.Serializable

@Serializable
data object Settings

fun NavController.navigateToSettings() {
    navigate(Settings)
}

fun NavGraphBuilder.settingsScreen(onBackClick: () -> Unit) {
    composable<Settings> {
        SettingsRoute(onBackClick = onBackClick)
    }
}

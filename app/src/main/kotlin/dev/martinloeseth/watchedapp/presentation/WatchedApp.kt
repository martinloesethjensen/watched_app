package dev.martinloeseth.watchedapp.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.martinloeseth.watchedapp.WatchedAppState
import dev.martinloeseth.watchedapp.navigation.WatchedNavHost
import dev.martinloeseth.watchedapp.navigation.navigateToSearch
import dev.martinloeseth.watchedapp.rememberWatchedAppState

@Composable
fun WatchedApp(
    appState: WatchedAppState = rememberWatchedAppState(),
) {
    Scaffold(
        modifier = Modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = { appState.navController.navigateToSearch() }) {
                Icon(Icons.Filled.Add, contentDescription = "Add movie to watchlist")
            }
        }) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            WatchedNavHost(appState.navController)
        }
    }
}
package dev.martinloeseth.watchedapp.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.martinloeseth.watchedapp.WatchedAppState
import dev.martinloeseth.watchedapp.navigation.WatchedNavHost
import dev.martinloeseth.watchedapp.rememberWatchedAppState

@Composable
fun WatchedApp(
    appState: WatchedAppState = rememberWatchedAppState(),
) {
    Scaffold(modifier = Modifier) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            WatchedNavHost(appState.navController)
        }

    }
}
package com.example.watchedapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberWatchedAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): WatchedAppState {
    return remember(navController, coroutineScope) {
        WatchedAppState(navController = navController, coroutineScope = coroutineScope)
    }
}

@Stable
class WatchedAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
)
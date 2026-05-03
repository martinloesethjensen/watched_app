package dev.martinloeseth.watchedapp.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import dev.martinloeseth.watchedapp.presentation.search.SearchRoute

const val searchNavigationRoute = "search_route"

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    this.navigate(searchNavigationRoute, navOptions)
}

fun NavGraphBuilder.searchScreen(onBackClick: () -> Unit) {
    composable(route = searchNavigationRoute) {
        SearchRoute(onBackClick)
    }
}
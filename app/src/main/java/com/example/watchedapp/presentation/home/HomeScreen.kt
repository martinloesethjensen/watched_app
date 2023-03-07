package com.example.watchedapp.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.watchedapp.R
import com.example.watchedapp.data.models.search.SearchMovieResult
import com.example.watchedapp.presentation.ui.components.Center
import com.example.watchedapp.presentation.ui.components.PosterGrid
import com.example.watchedapp.presentation.ui.theme.WatchedAppTheme

@Composable
internal fun HomeRoute(
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val configUiState by homeViewModel.configUiState.collectAsStateWithLifecycle()
    val homeUiState by homeViewModel.homeUiState.collectAsStateWithLifecycle()

    HomeScreen(
        modifier = modifier,
        configUiState = configUiState,
        homeUiState = homeUiState,
        onSearchClick = onSearchClick,
        onCardClick = homeViewModel::removeFromWatchlist
    )
}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    configUiState: ConfigUiState,
    homeUiState: HomeUiState,
    onSearchClick: () -> Unit,
    onCardClick: (SearchMovieResult) -> Unit
) {
    when (homeUiState) {
        HomeUiState.Loading -> LoadingScreen(modifier)
        HomeUiState.Failure -> ErrorScreen(modifier)
        is HomeUiState.Success -> SuccessScreen(
            modifier = modifier,
            watchlist = homeUiState.watchlist,
            onSearchClick = onSearchClick,
            onCardClick = onCardClick,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SuccessScreen(
    modifier: Modifier = Modifier,
    watchlist: List<SearchMovieResult>,
    onSearchClick: () -> Unit,
    onCardClick: (SearchMovieResult) -> Unit
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(text = stringResource(R.string.homeAppBarTitle))
        }, actions = {
            IconButton(onSearchClick) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.searchIconContentDescription),
                )
            }
        })
    }, content = { innerPadding ->
        if (watchlist.isEmpty()) {
            EmptyHomeBody(modifier.padding(innerPadding))
        } else {
            PosterGrid(
                modifier = modifier.padding(innerPadding),
                movieResults = watchlist,
                onCardClick = onCardClick
            )
        }
    })
}

@Composable
fun EmptyHomeBody(modifier: Modifier = Modifier) {
    Center(modifier) {
        Text(text = stringResource(R.string.emptyHomeText))
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Center(modifier) {
        CircularProgressIndicator(modifier = modifier)
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Center(modifier) {
        Text(stringResource(R.string.loading_failed))
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenLoading() {
    WatchedAppTheme {
        HomeScreen(
            configUiState = ConfigUiState.Loading,
            onSearchClick = {},
            homeUiState = HomeUiState.Loading,
            onCardClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    WatchedAppTheme {
        ErrorScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyHomeBodyPreview() {
    WatchedAppTheme {
        EmptyHomeBody()
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    WatchedAppTheme {
        SuccessScreen(
            watchlist = listOf(),
            onSearchClick = {},
            onCardClick = {},
        )
    }
}
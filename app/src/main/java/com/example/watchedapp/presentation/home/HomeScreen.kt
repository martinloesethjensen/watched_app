package com.example.watchedapp.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.watchedapp.R
import com.example.watchedapp.presentation.ui.theme.WatchedAppTheme

@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val configUiState by homeViewModel.configUiState.collectAsStateWithLifecycle()

    HomeScreen(modifier = modifier, configUiState = configUiState)
}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    configUiState: ConfigUiState,
) {
    when (configUiState) {
        ConfigUiState.Loading -> LoadingScreen(modifier)
        is ConfigUiState.Success -> SuccessScreen(result = listOf())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessScreen(modifier: Modifier = Modifier, result: List<String>) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(text = "AppBar title")
        }, actions = {
            IconButton(onClick = {
                // TODO: navigate to search screen

            }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    /// TODO: make into string
                    contentDescription = "Search for movies or shows",
                )
            }
        })
    }, content = { innerPadding ->
        if (result.isNotEmpty()) {
            LazyColumn(
                contentPadding = innerPadding, verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Text(text = result.toString(), color = Color.Green)
                }
            }
        } else {
            EmptyHomeBody(modifier.padding(innerPadding))
        }
    })
}

@Composable
fun EmptyHomeBody(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()
    ) {
        Text(text = stringResource(R.string.emptyHomeText))
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(modifier = modifier)
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()
    ) {
        Text(stringResource(R.string.loading_failed))
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    WatchedAppTheme {
        LoadingScreen()
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
        SuccessScreen(result = listOf())
    }
}
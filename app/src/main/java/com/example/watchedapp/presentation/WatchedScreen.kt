package com.example.watchedapp.presentation

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.watchedapp.R
import com.example.watchedapp.presentation.ui.theme.WatchedAppTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier, homeViewModel: HomeViewModel = viewModel()) {
    val homeUiState by homeViewModel.uiState.collectAsState()
    when (homeUiState) {
        is HomeUiState.Loading -> LoadingScreen(modifier)
        is HomeUiState.Success -> SuccessScreen(modifier, result = listOf("1", "2", "3"))
        is HomeUiState.Error -> ErrorScreen(modifier)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessScreen(modifier: Modifier = Modifier, result: List<String>) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(text = "AppBar title")
            },
                actions = {
                    IconButton(onClick = {
                        Log.d("WATCHED_SCREEN", "Clicked search button")
                        // TODO: navigate to search screen
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            /// TODO: make into string
                            contentDescription = "Search for movies or shows",
                        )
                    }
                })
        },
        content = { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

            }
        }
    )
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(modifier = modifier)
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
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
fun ResultScreenPreview() {
    WatchedAppTheme {
        SuccessScreen(result = listOf("1", "2", "3"))
    }
}
package com.example.watchedapp.presentation.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.watchedapp.R
import com.example.watchedapp.data.models.search.SearchMovieResult
import com.example.watchedapp.data.models.search.SearchMovieResults
import com.example.watchedapp.presentation.ui.components.Center
import com.example.watchedapp.presentation.ui.components.PosterGrid

@Composable
fun SearchRoute(
    onBackClick: () -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val searchUiState by searchViewModel.searchUiState.collectAsStateWithLifecycle()
    val queryState by searchViewModel.queryState.collectAsStateWithLifecycle()

    SearchScreen(
        searchUiState = searchUiState,
        queryState = queryState,
        onBackClick = onBackClick,
        onClearClick = searchViewModel::clearSearch,
        onSearch = searchViewModel::search,
        onCardClick = searchViewModel::addToWatchlist
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchUiState: SearchUiState,
    queryState: String,
    onBackClick: () -> Unit,
    onClearClick: () -> Unit,
    onSearch: (String) -> Unit,
    onCardClick: (SearchMovieResult) -> Unit,
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            val focusRequester = remember { FocusRequester() }
            OutlinedTextField(
                value = queryState,
                modifier = Modifier.focusRequester(focusRequester),
                onValueChange = { onSearch(it) },
                placeholder = { Text(text = stringResource(R.string.searchFieldPlaceholder)) },
                singleLine = true,
                maxLines = 1,
                trailingIcon = {
                    if (queryState.isNotBlank()) IconButton(
                        onClick = { onClearClick() },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = stringResource(R.string.clearSearchFieldContentDescription),
                        )
                    }
                },
            )
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
        }, navigationIcon = {
            IconButton(onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.backButtonContentDescription),
                )
            }
        })
    }, content = {
        when (searchUiState) {
            SearchUiState.Initial -> InitialScreen()
            SearchUiState.Failure -> ErrorScreen()
            SearchUiState.Loading -> LoadingScreen()
            is SearchUiState.Success -> SuccessScreen(
                modifier = Modifier.padding(it),
                searchResults = searchUiState.searchResults,
                onCardClick = onCardClick
            )
        }
    })
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Center(modifier) {
        Text(stringResource(R.string.loading_failed))
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Center(modifier) {
        CircularProgressIndicator(modifier = modifier)
    }
}

@Composable
fun InitialScreen(modifier: Modifier = Modifier) {
    Center(modifier) {
        Text(stringResource(R.string.initialSearchScreenText))
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SuccessScreen(
    modifier: Modifier = Modifier,
    searchResults: SearchMovieResults,
    onCardClick: (SearchMovieResult) -> Unit,
) {
    if (searchResults.results.isEmpty()) {
        EmptySearchBody()
    } else {
        PosterGrid(
            modifier = modifier,
            movieResults = searchResults.results,
            onCardClick = onCardClick
        )
    }
}

@Composable
fun EmptySearchBody(modifier: Modifier = Modifier) {
    Center(modifier) {
        Text("No results...")
    }
}
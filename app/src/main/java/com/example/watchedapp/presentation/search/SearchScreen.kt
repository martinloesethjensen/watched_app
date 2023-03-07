package com.example.watchedapp.presentation.search

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.example.watchedapp.R
import com.example.watchedapp.data.models.search.SearchMovieResult
import com.example.watchedapp.data.models.search.SearchMovieResults

@Composable
fun SearchRoute(
    onBackClick: () -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val searchUiState by searchViewModel.searchUiState.collectAsStateWithLifecycle()
    val queryState by searchViewModel.queryState.collectAsStateWithLifecycle()

    Log.d("queryState", queryState)

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
            OutlinedTextField(
                value = queryState,
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
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { onSearch(queryState) }),
            )
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
    Box(
        contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()
    ) {
        Text(stringResource(R.string.loading_failed))
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
fun InitialScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()
    ) {
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
        LazyVerticalStaggeredGrid(
            modifier = modifier,
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
        ) {

            items(searchResults.results) { result ->
                Box(modifier = Modifier.padding(8.dp)) {
                    Card(
                        onClick = { onCardClick(result) },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column {
                            SubcomposeAsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data("https://image.tmdb.org/t/p/w500${result.posterPath}")
                                    .crossfade(300)
                                    .build(),
                                contentDescription = result.title
                            ) {
                                val state = painter.state
                                if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                } else {
                                    SubcomposeAsyncImageContent()
                                }
                            }
                            Text(text = result.title)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptySearchBody(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()
    ) {
        Text("No results...")
    }
}
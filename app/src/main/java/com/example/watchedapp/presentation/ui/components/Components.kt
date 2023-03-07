package com.example.watchedapp.presentation.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.example.watchedapp.data.models.search.SearchMovieResult

@Composable
fun Center(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()
    ) {
        content()
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PosterGrid(
    modifier: Modifier = Modifier,
    movieResults: List<SearchMovieResult>,
    onCardClick: (SearchMovieResult) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
    ) {
        items(movieResults) { result ->
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
package dev.martinloeseth.watchedapp.presentation.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import dev.martinloeseth.watchedapp.data.models.search.SearchMovieResult

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

@OptIn(ExperimentalFoundationApi::class)
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
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = movieResults,
            key = { it.id },
        ) { result ->
            Card(
                onClick = { onCardClick(result) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://image.tmdb.org/t/p/w500${result.posterPath}")
                            .crossfade(300)
                            .memoryCachePolicy(CachePolicy.ENABLED)
                            .diskCachePolicy(CachePolicy.ENABLED)
                            .build(),
                        contentDescription = result.title,
                        contentScale = ContentScale.Crop,
                        placeholder = rememberVectorPainter(Icons.Default.Movie),
                        error = rememberVectorPainter(Icons.Default.BrokenImage),
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2f / 3f), // standard movie poster ratio
                    )
                    Text(
                        text = result.title,
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                    )
                    Text(
                        text = result.releaseDate,
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                    )
                }
            }
        }
    }
}
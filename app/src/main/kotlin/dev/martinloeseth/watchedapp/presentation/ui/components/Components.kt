package dev.martinloeseth.watchedapp.presentation.ui.components

import androidx.compose.animation.ExperimentalSharedTransitionApi
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import dev.martinloeseth.watchedapp.domain.models.Movie
import dev.martinloeseth.watchedapp.domain.models.toLocalDateOrNull
import dev.martinloeseth.watchedapp.presentation.ui.LocalAnimatedContentScope
import dev.martinloeseth.watchedapp.presentation.ui.LocalSharedTransitionScope

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
    movieResults: List<Movie>,
    onCardClick: (Movie) -> Unit,
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
        ) { movie ->
            MovieCard(
                title = movie.title,
                posterPath = movie.posterPath,
                releaseYear = movie.releaseDate.toLocalDateOrNull()?.year,
                sharedElementKey = "poster-${movie.id}",
                onClick = { onCardClick(movie) },
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieCard(
    title: String,
    posterPath: String?,
    releaseYear: Int?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    sharedElementKey: String? = null,
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedContentScope = LocalAnimatedContentScope.current

    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
    ) {
        Column {
            val imageModifier = if (sharedElementKey != null
                && sharedTransitionScope != null
                && animatedContentScope != null
            ) {
                with(sharedTransitionScope) {
                    Modifier.sharedElement(
                        rememberSharedContentState(key = sharedElementKey),
                        animatedVisibilityScope = animatedContentScope,
                    )
                }
            } else {
                Modifier
            }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w500$posterPath")
                    .crossfade(300)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                placeholder = rememberVectorPainter(Icons.Default.Movie),
                error = rememberVectorPainter(Icons.Default.BrokenImage),
                modifier = imageModifier
                    .fillMaxWidth()
                    .aspectRatio(2f / 3f)
                    .clip(MaterialTheme.shapes.medium),
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            )
            Text(
                text = releaseYear?.toString() ?: "Unknown",
                style = MaterialTheme.typography.labelMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            )
        }
    }
}

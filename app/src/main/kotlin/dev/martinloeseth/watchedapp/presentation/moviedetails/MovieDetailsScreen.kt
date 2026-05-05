package dev.martinloeseth.watchedapp.presentation.moviedetails

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import dev.martinloeseth.watchedapp.domain.models.Genre
import dev.martinloeseth.watchedapp.domain.models.MovieDetails
import dev.martinloeseth.watchedapp.presentation.ui.LocalAnimatedContentScope
import dev.martinloeseth.watchedapp.presentation.ui.LocalSharedTransitionScope
import dev.martinloeseth.watchedapp.presentation.ui.components.Center
import androidx.compose.material.icons.filled.BrokenImage

@Composable
fun MovieDetailsRoute(
    onBackClick: () -> Unit,
    viewModel: MovieDetailsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MovieDetailsScreen(
        uiState = uiState,
        onBackClick = onBackClick,
    )
}

@Composable
fun MovieDetailsScreen(
    uiState: MovieDetailsUiState,
    onBackClick: () -> Unit,
) {
    when (uiState) {
        MovieDetailsUiState.Loading -> Center { CircularProgressIndicator() }
        MovieDetailsUiState.Failure -> Center { Text("Failed to load movie details.") }
        is MovieDetailsUiState.Success -> MovieDetailsContent(
            movieDetails = uiState.movieDetails,
            isInWatchlist = uiState.isInWatchlist,
            onBackClick = onBackClick,
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun MovieDetailsContent(
    movieDetails: MovieDetails,
    isInWatchlist: Boolean,
    onBackClick: () -> Unit,
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedContentScope = LocalAnimatedContentScope.current

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Box {
                val imageModifier = if (sharedTransitionScope != null && animatedContentScope != null) {
                    with(sharedTransitionScope) {
                        Modifier
                            .sharedElement(
                                rememberSharedContentState(key = "poster-${movieDetails.id}"),
                                animatedVisibilityScope = animatedContentScope,
                            )
                            .fillMaxWidth()
                            .aspectRatio(2f / 3f)
                    }
                } else {
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f / 3f)
                }

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://image.tmdb.org/t/p/w500${movieDetails.posterPath}")
                        .crossfade(300)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    contentDescription = movieDetails.title,
                    contentScale = ContentScale.Crop,
                    error = rememberVectorPainter(Icons.Default.BrokenImage),
                    modifier = imageModifier,
                )

                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(8.dp),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = movieDetails.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                )

                if (movieDetails.tagline.isNotBlank()) {
                    Text(
                        text = movieDetails.tagline,
                        style = MaterialTheme.typography.bodyMedium,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    if (movieDetails.releaseDate.isNotBlank()) {
                        Text(
                            text = movieDetails.releaseDate,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    movieDetails.runtime?.let { runtime ->
                        Text(
                            text = "${runtime}m",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }

                Text(
                    text = "★ ${"%.1f".format(movieDetails.voteAverage)} (${movieDetails.voteCount} votes)",
                    style = MaterialTheme.typography.bodyMedium,
                )

                if (movieDetails.genres.isNotEmpty()) {
                    GenreRow(genres = movieDetails.genres)
                }

                if (isInWatchlist) {
                    WatchlistBadge()
                }

                if (movieDetails.overview.isNotBlank()) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = movieDetails.overview,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

@Composable
private fun GenreRow(genres: List<Genre>) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(genres) { genre ->
            SuggestionChip(
                onClick = {},
                label = { Text(genre.name) },
            )
        }
    }
}

@Composable
private fun WatchlistBadge() {
    AssistChip(
        onClick = {},
        label = { Text("In Watchlist") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Bookmark,
                contentDescription = null,
                modifier = Modifier.size(AssistChipDefaults.IconSize),
            )
        },
    )
}

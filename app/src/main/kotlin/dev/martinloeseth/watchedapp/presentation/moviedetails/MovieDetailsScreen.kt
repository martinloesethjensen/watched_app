package dev.martinloeseth.watchedapp.presentation.moviedetails

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import dev.martinloeseth.watchedapp.domain.models.Movie
import dev.martinloeseth.watchedapp.domain.models.MovieDetails
import dev.martinloeseth.watchedapp.domain.models.toLocalDateOrNull
import dev.martinloeseth.watchedapp.presentation.ui.LocalAnimatedContentScope
import dev.martinloeseth.watchedapp.presentation.ui.LocalSharedTransitionScope

@Composable
fun MovieDetailsRoute(
    onBackClick: () -> Unit,
    viewModel: MovieDetailsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MovieDetailsScreen(
        movie = viewModel.movie,
        uiState = uiState,
        onBackClick = onBackClick,
        onToggleWatchlist = viewModel::toggleWatchlist,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieDetailsScreen(
    movie: Movie,
    uiState: MovieDetailsUiState,
    onBackClick: () -> Unit,
    onToggleWatchlist: () -> Unit,
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedContentScope = LocalAnimatedContentScope.current

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Box {
                val imageModifier =
                    if (sharedTransitionScope != null && animatedContentScope != null) {
                        with(sharedTransitionScope) {
                            Modifier
                                .sharedElement(
                                    rememberSharedContentState(key = "poster-${movie.id}"),
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
                        .data("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                        .crossfade(300)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop,
                    error = rememberVectorPainter(Icons.Default.BrokenImage),
                    modifier = imageModifier.clip(MaterialTheme.shapes.medium),
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
                    text = movie.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                )

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    if (movie.releaseDate.isNotBlank()) {
                        Text(
                            text = movie.releaseDate.toLocalDateOrNull()?.year?.toString()
                                ?: "Unknown",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }

                Text(
                    text = "★ ${"%.1f".format(movie.voteAverage)} (${movie.voteCount} votes)",
                    style = MaterialTheme.typography.bodyMedium,
                )

                when (uiState) {
                    MovieDetailsUiState.Loading -> Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }

                    MovieDetailsUiState.Failure -> Text(
                        text = "Failed to load movie details.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                    )

                    is MovieDetailsUiState.Success -> MovieDetails(
                        movieDetails = uiState.movieDetails,
                        isInWatchlist = uiState.isInWatchlist,
                        onToggleWatchlist = onToggleWatchlist,
                    )
                }

                Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
            }
        }
    }
}

@Composable
private fun MovieDetails(
    movieDetails: MovieDetails,
    isInWatchlist: Boolean,
    onToggleWatchlist: () -> Unit,
) {
    if (movieDetails.tagline.isNotBlank()) {
        Text(
            text = movieDetails.tagline,
            style = MaterialTheme.typography.bodyMedium,
            fontStyle = FontStyle.Italic,
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

    if (movieDetails.genres.isNotEmpty()) {
        GenreRow(genres = movieDetails.genres)
    }

    WatchlistChip(
        inWatchlist = isInWatchlist,
        onClick = onToggleWatchlist,
    )

    if (movieDetails.overview.isNotBlank()) {
        Spacer(Modifier.height(4.dp))
        Text(
            text = movieDetails.overview,
            style = MaterialTheme.typography.bodyMedium,
        )
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
private fun WatchlistChip(inWatchlist: Boolean, onClick: () -> Unit) {
    FilterChip(
        selected = inWatchlist,
        onClick = onClick,
        label = { Text(if (inWatchlist) "In Watchlist" else "Add to Watchlist") },
        leadingIcon = {
            Icon(
                imageVector = if (inWatchlist) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                contentDescription = null,
                modifier = Modifier.size(FilterChipDefaults.IconSize),
            )
        },
    )
}

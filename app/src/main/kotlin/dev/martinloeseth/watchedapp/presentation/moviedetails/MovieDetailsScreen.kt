package dev.martinloeseth.watchedapp.presentation.moviedetails

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import dev.martinloeseth.watchedapp.R
import dev.martinloeseth.watchedapp.domain.models.Genre
import dev.martinloeseth.watchedapp.domain.models.Movie
import dev.martinloeseth.watchedapp.domain.models.MovieDetails
import dev.martinloeseth.watchedapp.domain.models.WatchEntry
import dev.martinloeseth.watchedapp.domain.models.toLocalDateOrNull
import dev.martinloeseth.watchedapp.presentation.ui.LocalAnimatedContentScope
import dev.martinloeseth.watchedapp.presentation.ui.LocalSharedTransitionScope
import java.text.SimpleDateFormat
import java.util.Date

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
        onLogWatched = viewModel::logWatched,
        onSetRating = viewModel::setRating,
        onRemoveWatchEntry = viewModel::removeWatchEntry,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieDetailsScreen(
    movie: Movie,
    uiState: MovieDetailsUiState,
    onBackClick: () -> Unit,
    onToggleWatchlist: () -> Unit,
    onLogWatched: () -> Unit,
    onSetRating: (Int) -> Unit,
    onRemoveWatchEntry: (Int) -> Unit,
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedContentScope = LocalAnimatedContentScope.current
    val surfaceColor = MaterialTheme.colorScheme.surface

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            // Backdrop hero + poster/title row
            Box(modifier = Modifier.fillMaxWidth()) {
                val heroPath = movie.backdropPath ?: movie.posterPath
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://image.tmdb.org/t/p/w780$heroPath")
                        .crossfade(300)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    error = rememberVectorPainter(Icons.Default.BrokenImage),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f),
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .align(Alignment.TopCenter)
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Black.copy(alpha = 0.45f), Color.Transparent)
                            )
                        )
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(listOf(Color.Transparent, surfaceColor))
                        )
                )
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .statusBarsPadding()
                        .padding(4.dp),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Top,
            ) {
                val posterModifier =
                    if (sharedTransitionScope != null && animatedContentScope != null) {
                        with(sharedTransitionScope) {
                            Modifier.sharedElement(
                                rememberSharedContentState(key = "poster-${movie.id}"),
                                animatedVisibilityScope = animatedContentScope,
                            )
                        }
                    } else Modifier

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
                    modifier = posterModifier
                        .width(110.dp)
                        .aspectRatio(2f / 3f)
                        .clip(MaterialTheme.shapes.medium),
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )

                    val year = movie.releaseDate.toLocalDateOrNull()?.year
                    if (year != null) {
                        Text(
                            text = year.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }

                    if (movie.voteCount > 0) {
                        Text(
                            text = "★ ${"%.1f".format(movie.voteAverage)} · ${movie.voteCount} votes",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
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

                    is MovieDetailsUiState.Success -> MovieDetailsContent(
                        movieDetails = uiState.movieDetails,
                        isInWatchlist = uiState.isInWatchlist,
                        isWatched = uiState.isWatched,
                        watchHistory = uiState.watchHistory,
                        rating = uiState.userRating,
                        onToggleWatchlist = onToggleWatchlist,
                        onLogWatched = onLogWatched,
                        onSetRating = onSetRating,
                        onRemoveWatchEntry = onRemoveWatchEntry,
                    )
                }

                Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
            }
        }
    }
}

@Composable
private fun MovieDetailsContent(
    movieDetails: MovieDetails,
    isInWatchlist: Boolean,
    isWatched: Boolean,
    watchHistory: List<WatchEntry>,
    rating: Int?,
    onToggleWatchlist: () -> Unit,
    onLogWatched: () -> Unit,
    onSetRating: (Int) -> Unit,
    onRemoveWatchEntry: (Int) -> Unit,
) {
    movieDetails.runtime?.let { runtime ->
        val hours = runtime / 60
        val minutes = runtime % 60
        Text(
            text = if (hours > 0) "${hours}h ${minutes}m" else "${minutes}m",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        WatchlistChip(inWatchlist = isInWatchlist, onClick = onToggleWatchlist)
        if (isInWatchlist) {
            OutlinedButton(onClick = onLogWatched) {
                Text("Log as Watched")
            }
        }
    }

    if (isWatched) {
        StarRatingRow(rating = rating ?: 0, onSetRating = onSetRating)
    }

    HorizontalDivider()

    if (movieDetails.genres.isNotEmpty()) {
        GenreRow(genres = movieDetails.genres)
    }

    if (movieDetails.tagline.isNotBlank()) {
        Text(
            text = "\"${movieDetails.tagline}\"",
            style = MaterialTheme.typography.bodyMedium,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }

    if (movieDetails.overview.isNotBlank()) {
        Text(
            text = movieDetails.overview,
            style = MaterialTheme.typography.bodyMedium,
        )
    }

    if (isWatched && watchHistory.isNotEmpty()) {
        HorizontalDivider()
        Text(
            text = "Watched ${watchHistory.size} time${if (watchHistory.size != 1) "s" else ""}",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            watchHistory.forEach { entry ->
                ListItem(
                    headlineContent = {
                        Text(
                            text = SimpleDateFormat(
                                "d MMM yyyy",
                                LocalLocale.current.platformLocale
                            )
                                .format(Date(entry.watchedAt)),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    },
                    trailingContent = {
                        IconButton(onClick = { onRemoveWatchEntry(entry.id) }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = stringResource(R.string.deleteWatchEntryIconContentDescription),
                            )
                        }
                    }
                )
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

@Composable
private fun StarRatingRow(rating: Int, onSetRating: (Int) -> Unit) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(5) { i ->
            val star = i + 1
            Icon(
                imageVector = if (star <= rating) Icons.Rounded.Star else Icons.Rounded.StarOutline,
                contentDescription = "Star $star",
                modifier = Modifier.clickable { onSetRating(star) },
            )
        }
    }
}

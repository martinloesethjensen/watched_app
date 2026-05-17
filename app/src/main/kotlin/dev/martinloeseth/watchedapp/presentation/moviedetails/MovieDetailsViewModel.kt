package dev.martinloeseth.watchedapp.presentation.moviedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.martinloeseth.watchedapp.domain.core.result.Result
import dev.martinloeseth.watchedapp.domain.core.result.asResult
import dev.martinloeseth.watchedapp.domain.models.Movie
import dev.martinloeseth.watchedapp.domain.usecases.AddToWatchlistUseCase
import dev.martinloeseth.watchedapp.domain.usecases.DeleteWatchHistoryEntryUseCase
import dev.martinloeseth.watchedapp.domain.usecases.GetMovieDetailsUseCase
import dev.martinloeseth.watchedapp.domain.usecases.GetWatchHistoryUseCase
import dev.martinloeseth.watchedapp.domain.usecases.GetWatchlistUseCase
import dev.martinloeseth.watchedapp.domain.usecases.LogWatchedUseCase
import dev.martinloeseth.watchedapp.domain.usecases.RemoveFromWatchlistUseCase
import dev.martinloeseth.watchedapp.domain.usecases.SetUserRatingUseCase
import dev.martinloeseth.watchedapp.navigation.Details
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getMovieDetailsUseCase: GetMovieDetailsUseCase,
    getWatchlistUseCase: GetWatchlistUseCase,
    private val logWatchedUseCase: LogWatchedUseCase,
    getWatchHistoryUseCase: GetWatchHistoryUseCase,
    private val setUserRatingUseCase: SetUserRatingUseCase,
    private val addToWatchlistUseCase: AddToWatchlistUseCase,
    private val removeFromWatchlistUseCase: RemoveFromWatchlistUseCase,
    private val deleteWatchHistoryEntryUseCase: DeleteWatchHistoryEntryUseCase,
) : ViewModel() {

    val movie: Movie = Json.decodeFromString(savedStateHandle.toRoute<Details>().movieJson)
    private val movieId: Int = movie.id

    val uiState: StateFlow<MovieDetailsUiState> = combine(
        getMovieDetailsUseCase(movieId).asResult(),
        getWatchlistUseCase(),
        getWatchHistoryUseCase(movieId),
    ) { detailsResult, watchlist, watchHistory ->
        when (detailsResult) {
            is Result.Error -> MovieDetailsUiState.Failure
            Result.Loading -> MovieDetailsUiState.Loading
            is Result.Success -> {
                val movieInWatchList = watchlist.firstOrNull { it.id == movieId };
                MovieDetailsUiState.Success(
                    movieDetails = detailsResult.data,
                    isInWatchlist = movieInWatchList != null,
                    userRating = movieInWatchList?.userRating,
                    watchHistory = watchHistory,
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MovieDetailsUiState.Loading,
    )

    fun toggleWatchlist() {
        val inWatchlist = (uiState.value as? MovieDetailsUiState.Success)?.isInWatchlist ?: return
        viewModelScope.launch {
            if (inWatchlist) {
                removeFromWatchlistUseCase(movie.id)
            } else {
                addToWatchlistUseCase(movie)
            }
        }
    }

    // TODO(mlj): implement datetime selector in UI and parse as timestamp into 'watchedAt'
    fun logWatched() {
        viewModelScope.launch {
            logWatchedUseCase(movieId)
        }
    }

    fun setRating(rating: Int) {
        viewModelScope.launch {
            setUserRatingUseCase(movieId, rating)
        }
    }

    fun removeWatchEntry(id: Int) {
        viewModelScope.launch {
            deleteWatchHistoryEntryUseCase(id)
        }
    }
}

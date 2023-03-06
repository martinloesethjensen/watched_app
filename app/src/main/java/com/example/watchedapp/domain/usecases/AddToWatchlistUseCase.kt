package com.example.watchedapp.domain.usecases

import com.example.watchedapp.data.models.search.SearchMovieResult
import com.example.watchedapp.data.repositories.watchlist.WatchlistRepository
import javax.inject.Inject

class AddToWatchlistUseCase @Inject constructor(
    private val watchlistRepository: WatchlistRepository,
) {
    suspend operator fun invoke(watchlistItem: SearchMovieResult) =
        watchlistRepository.addToWatchlist(watchlistItem)
}
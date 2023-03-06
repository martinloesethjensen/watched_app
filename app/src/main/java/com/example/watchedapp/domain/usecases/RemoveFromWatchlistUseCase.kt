package com.example.watchedapp.domain.usecases

import com.example.watchedapp.data.repositories.watchlist.WatchlistRepository
import javax.inject.Inject

class RemoveFromWatchlistUseCase @Inject constructor(
    private val watchlistRepository: WatchlistRepository,
) {
    suspend operator fun invoke(id: Int) = watchlistRepository.removeFromWatchlist(id)
}
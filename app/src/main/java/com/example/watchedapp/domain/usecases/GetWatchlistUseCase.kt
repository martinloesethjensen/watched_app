package com.example.watchedapp.domain.usecases

import com.example.watchedapp.data.repositories.watchlist.WatchlistRepository
import javax.inject.Inject

class GetWatchlistUseCase @Inject constructor(
    private val watchlistRepository: WatchlistRepository,
) {
    operator fun invoke() = watchlistRepository.getWatchlist()
}
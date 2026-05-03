package dev.martinloeseth.watchedapp.domain.usecases

import dev.martinloeseth.watchedapp.data.repositories.watchlist.WatchlistRepository
import javax.inject.Inject

class GetWatchlistUseCase @Inject constructor(
    private val watchlistRepository: WatchlistRepository,
) {
    operator fun invoke() = watchlistRepository.getWatchlist()
}
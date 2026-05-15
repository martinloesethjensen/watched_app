package dev.martinloeseth.watchedapp.domain.usecases

import dev.martinloeseth.watchedapp.domain.repositories.WatchlistRepository
import dev.martinloeseth.watchedapp.domain.models.Movie
import javax.inject.Inject

class AddToWatchlistUseCase @Inject constructor(
    private val watchlistRepository: WatchlistRepository,
) {
    suspend operator fun invoke(watchlistItem: Movie) =
        watchlistRepository.addToWatchlist(watchlistItem)
}

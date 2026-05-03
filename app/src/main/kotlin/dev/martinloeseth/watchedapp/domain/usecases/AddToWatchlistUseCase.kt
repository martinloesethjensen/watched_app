package dev.martinloeseth.watchedapp.domain.usecases

import dev.martinloeseth.watchedapp.data.models.search.SearchMovieResult
import dev.martinloeseth.watchedapp.data.repositories.watchlist.WatchlistRepository
import javax.inject.Inject

class AddToWatchlistUseCase @Inject constructor(
    private val watchlistRepository: WatchlistRepository,
) {
    suspend operator fun invoke(watchlistItem: SearchMovieResult) =
        watchlistRepository.addToWatchlist(watchlistItem)
}
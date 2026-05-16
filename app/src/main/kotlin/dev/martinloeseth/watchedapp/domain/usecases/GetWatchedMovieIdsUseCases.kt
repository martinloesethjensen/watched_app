package dev.martinloeseth.watchedapp.domain.usecases

import dev.martinloeseth.watchedapp.domain.repositories.WatchHistoryRepository
import javax.inject.Inject

class GetWatchedMovieIdsUseCases @Inject constructor(private val repository: WatchHistoryRepository) {
    operator fun invoke() = repository.getWatchedMovieIds()
}
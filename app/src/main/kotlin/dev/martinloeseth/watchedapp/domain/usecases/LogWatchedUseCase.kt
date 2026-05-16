package dev.martinloeseth.watchedapp.domain.usecases

import dev.martinloeseth.watchedapp.domain.repositories.WatchHistoryRepository
import javax.inject.Inject

class LogWatchedUseCase @Inject constructor(private val repository: WatchHistoryRepository) {
    suspend operator fun invoke(movieId: Int, watchedAt: Long? = null) =
        repository.logWatched(movieId, watchedAt)
}
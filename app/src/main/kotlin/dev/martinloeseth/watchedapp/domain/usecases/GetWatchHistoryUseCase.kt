package dev.martinloeseth.watchedapp.domain.usecases

import dev.martinloeseth.watchedapp.domain.repositories.WatchHistoryRepository
import javax.inject.Inject

class GetWatchHistoryUseCase @Inject constructor(private val repository: WatchHistoryRepository) {
    operator fun invoke(movieId: Int) = repository.getWatchHistory(movieId)
}
package dev.martinloeseth.watchedapp.domain.usecases

import dev.martinloeseth.watchedapp.domain.repositories.WatchHistoryRepository
import javax.inject.Inject

class DeleteWatchHistoryEntry @Inject constructor(
    private val repository: WatchHistoryRepository
) {
    suspend operator fun invoke(id: Int) = repository.deleteEntry(id)
}
package dev.martinloeseth.watchedapp.domain.usecases

import dev.martinloeseth.watchedapp.domain.repositories.WatchlistRepository
import javax.inject.Inject

class SetUserRatingUseCase @Inject constructor(private val repository: WatchlistRepository) {
    suspend operator fun invoke(id: Int, rating: Int) = repository.setUserRating(id, rating)
}
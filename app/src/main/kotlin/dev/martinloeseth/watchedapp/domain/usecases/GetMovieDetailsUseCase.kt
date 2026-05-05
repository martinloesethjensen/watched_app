package dev.martinloeseth.watchedapp.domain.usecases

import dev.martinloeseth.watchedapp.data.repositories.moviedetails.MovieDetailsRepository
import dev.martinloeseth.watchedapp.domain.models.MovieDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository,
) {
    operator fun invoke(movieId: Int): Flow<MovieDetails> =
        movieDetailsRepository.getMovieDetails(movieId)
}

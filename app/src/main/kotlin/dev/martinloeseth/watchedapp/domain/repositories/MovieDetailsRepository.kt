package dev.martinloeseth.watchedapp.domain.repositories

import dev.martinloeseth.watchedapp.domain.models.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {
    fun getMovieDetails(movieId: Int): Flow<MovieDetails>
}

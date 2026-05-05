package dev.martinloeseth.watchedapp.data.repositories.moviedetails

import dev.martinloeseth.watchedapp.domain.models.MovieDetails
import dev.martinloeseth.watchedapp.network.MovieDetailsNetworkDataSource
import dev.martinloeseth.watchedapp.network.models.asMovieDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface MovieDetailsRepository {
    fun getMovieDetails(movieId: Int): Flow<MovieDetails>
}

class RemoteMovieDetailsRepository @Inject constructor(
    private val network: MovieDetailsNetworkDataSource,
) : MovieDetailsRepository {
    override fun getMovieDetails(movieId: Int) = flow {
        emit(network.getMovieDetails(movieId).asMovieDetails())
    }
}

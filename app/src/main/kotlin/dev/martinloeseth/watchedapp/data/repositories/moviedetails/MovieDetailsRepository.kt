package dev.martinloeseth.watchedapp.data.repositories.moviedetails

import dev.martinloeseth.watchedapp.domain.models.MovieDetails
import dev.martinloeseth.watchedapp.domain.repositories.MovieDetailsRepository
import dev.martinloeseth.watchedapp.network.MovieDetailsNetworkDataSource
import dev.martinloeseth.watchedapp.network.models.asMovieDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class RemoteMovieDetailsRepository @Inject constructor(
    private val network: MovieDetailsNetworkDataSource,
) : MovieDetailsRepository {
    override fun getMovieDetails(movieId: Int): Flow<MovieDetails> = flow {
        try {
            emit(network.getMovieDetails(movieId).asMovieDetails())
        } catch (_: IOException) {
            throw IOException("No network connection")
        }
    }
}

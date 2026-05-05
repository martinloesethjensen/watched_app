package dev.martinloeseth.watchedapp.network

import dev.martinloeseth.watchedapp.network.models.MovieDetailsNetworkModel

interface MovieDetailsNetworkDataSource {
    suspend fun getMovieDetails(movieId: Int): MovieDetailsNetworkModel
}

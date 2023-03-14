package com.example.watchedapp.domain.usecases

import com.example.watchedapp.data.models.search.SearchMovieResult
import com.example.watchedapp.data.repositories.watchlist.TestWatchlistRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RemoveFromWatchlistUseCaseTest {
    private val watchlistRepository = TestWatchlistRepository()

    val useCase = RemoveFromWatchlistUseCase(watchlistRepository)

    @Test
    fun shouldSuccessfullyRemoveFromWatchlist() = runTest {
        watchlistRepository.setWatchlistResource(
            listOf(
                testSearchMovieResult(1),
                testSearchMovieResult(2),
            )
        )

        val removedId = 1

        useCase(removedId)

        val watchlist = watchlistRepository.getWatchlist().first()

        assert(watchlist.size == 1)
        assert(watchlist.none { it.id == removedId })
        assert(watchlist.count { it.id == 2 } == 1)
    }

    @Test
    fun shouldNotRemoveFromWatchlist_IfNoneExists() = runTest {
        watchlistRepository.setWatchlistResource(
            listOf(
                testSearchMovieResult(1),
                testSearchMovieResult(2),
                testSearchMovieResult(3),
            )
        )

        val removedId = 1
        useCase(removedId)
        val removedNonExistingId = 11
        useCase(removedNonExistingId)

        val watchlist = watchlistRepository.getWatchlist().first()

        assert(watchlist.size == 2)
        assert(watchlist.none { it.id == removedId })
        assert(watchlist.none { it.id == removedNonExistingId })
    }
}

private fun testSearchMovieResult(id: Int = 0) = SearchMovieResult(
    id = id,
    title = "",
    originalLanguage = "",
    originalTitle = "",
    overview = "",
    releaseDate = ""
)

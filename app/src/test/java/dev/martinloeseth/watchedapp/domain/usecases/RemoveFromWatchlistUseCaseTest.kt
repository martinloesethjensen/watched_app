package dev.martinloeseth.watchedapp.domain.usecases

import dev.martinloeseth.watchedapp.data.repositories.watchlist.TestWatchlistRepository
import dev.martinloeseth.watchedapp.domain.models.Movie
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
                testMovie(1),
                testMovie(2),
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
                testMovie(1),
                testMovie(2),
                testMovie(3),
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

private fun testMovie(id: Int = 0) = Movie(
    id = id,
    title = "",
    originalLanguage = "",
    originalTitle = "",
    overview = "",
    releaseDate = ""
)

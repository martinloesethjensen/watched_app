package dev.martinloeseth.watchedapp.domain.usecases

import dev.martinloeseth.watchedapp.data.repositories.watchlist.TestWatchlistRepository
import dev.martinloeseth.watchedapp.domain.models.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class AddToWatchlistUseCaseTest {
    private val watchlistRepository = TestWatchlistRepository()

    val useCase = AddToWatchlistUseCase(watchlistRepository)

    @Test
    fun shouldSuccessfullyAddToWatchlist() = runTest {
        watchlistRepository.setWatchlistResource(listOf())

        useCase(sampleWatchlistItem1)

        val watchlist = watchlistRepository.watchlistFlow.first()

        assert(watchlist.size == 1)
        assertEquals(
            sampleWatchlistItem1,
            watchlist.first(),
        )
    }

    @Test
    fun shouldNotAddTwoOfTheSame_ToWatchlist() = runTest {
        watchlistRepository.setWatchlistResource(listOf())

        useCase(sampleWatchlistItem1)
        useCase(sampleWatchlistItem1)

        val watchlist = watchlistRepository.watchlistFlow.first()

        assert(watchlist.size == 1)
        assertEquals(
            sampleWatchlistItem1,
            watchlist.first(),
        )
    }

    @Test
    fun shouldAddTwo_ToWatchlist() = runTest {
        watchlistRepository.setWatchlistResource(listOf())

        useCase(sampleWatchlistItem1)
        useCase(sampleWatchlistItem2)

        val watchlist = watchlistRepository.watchlistFlow.first()

        assert(watchlist.size == 2)
        assertEquals(
            sampleWatchlistItem1,
            watchlist.first(),
        )
        assertEquals(
            sampleWatchlistItem2,
            watchlist.last(),
        )
    }
}

private val sampleWatchlistItem1 = Movie(
    id = 1,
    title = "",
    originalLanguage = "",
    originalTitle = "",
    overview = "",
    releaseDate = ""
)

private val sampleWatchlistItem2 = Movie(
    id = 2,
    title = "",
    originalLanguage = "",
    originalTitle = "",
    overview = "",
    releaseDate = ""
)

package com.example.watchedapp.domain.usecases

import com.example.watchedapp.data.models.search.SearchMovieResult
import com.example.watchedapp.data.repositories.watchlist.TestWatchlistRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetWatchlistUseCaseTest {
    private val watchlistRepository = TestWatchlistRepository()

    val useCase = GetWatchlistUseCase(watchlistRepository)

    @Test
    fun shouldSuccessfullyReturnWatchlist() = runTest {
        val watchlist = listOf(
            sampleWatchlistItem1,
            sampleWatchlistItem2,
        )

        watchlistRepository.setWatchlistResource(watchlist)

        val result = useCase().first()

        assertEquals(
            watchlist,
            result,
        )
    }
}

private val sampleWatchlistItem1 = SearchMovieResult(
    id = 1,
    title = "",
    originalLanguage = "",
    originalTitle = "",
    overview = "",
    releaseDate = ""
)

private val sampleWatchlistItem2 = SearchMovieResult(
    id = 2,
    title = "",
    originalLanguage = "",
    originalTitle = "",
    overview = "",
    releaseDate = ""
)
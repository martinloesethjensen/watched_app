package dev.martinloeseth.watchedapp.domain.usecases

import dev.martinloeseth.watchedapp.data.models.search.SearchMovieResult
import dev.martinloeseth.watchedapp.data.models.search.SearchMovieResults
import dev.martinloeseth.watchedapp.data.repositories.search.SearchQuery
import dev.martinloeseth.watchedapp.data.repositories.search.TestSearchRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetSearchResultsUseCaseTest {
    private val searchRepository = TestSearchRepository()

    val useCase = GetSearchResultsUseCase(searchRepository)

    @Test
    fun shouldSuccessfullySearch_AndReturnSearchMovieResults() = runTest {
        searchRepository.sendSearchResources(populatedSearchResult)

        val searchQuery = SearchQuery("dune")

        val searchResult = useCase(searchQuery).first()

        assert(searchResult.results.all { it.title.contains(searchQuery.query, ignoreCase = true) })
    }

    @Test
    fun shouldNotFindAnySearchResults() = runTest {
        searchRepository.sendSearchResources(populatedSearchResult)

        val searchQuery = SearchQuery("nothing that exist")

        val searchResult = useCase(searchQuery).first()

        assert(searchResult.results.none {
            it.title.contains(
                searchQuery.query,
                ignoreCase = true
            )
        })
        assert(searchResult.results.isEmpty())
    }
}

private val populatedSearchResult = SearchMovieResults(
    results = listOf(
        testSearchMovieResult("Dune"),
        testSearchMovieResult("dune"),
        testSearchMovieResult("Dune: Part Two"),
        testSearchMovieResult("Harry Potter"),
    ).mapIndexed { idx, movie -> movie.copy(id = idx + 1) }
)

private fun testSearchMovieResult(title: String = "") = SearchMovieResult(
    id = 0,
    title = title,
    originalLanguage = "",
    originalTitle = "",
    overview = "",
    releaseDate = ""
)
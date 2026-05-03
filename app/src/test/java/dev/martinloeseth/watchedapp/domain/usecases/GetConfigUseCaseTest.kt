package dev.martinloeseth.watchedapp.domain.usecases

import dev.martinloeseth.watchedapp.data.models.config.ConfigResult
import dev.martinloeseth.watchedapp.data.models.config.ImagesConfig
import dev.martinloeseth.watchedapp.data.repositories.config.TestConfigRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetConfigUseCaseTest {
    private val configRepository = TestConfigRepository()

    val useCase = GetConfigUseCase(configRepository)

    @Test
    fun configResultIsReturned() = runTest {
        val config = useCase()

        configRepository.sendConfigResource(sampleConfig)

        assertEquals(
            sampleConfig,
            config.first(),
        )
    }
}

private val sampleConfig = ConfigResult(
    images = ImagesConfig(
        baseUrl = "testBaseUrl",
        secureBaseUrl = ""
    )
)
package com.example.watchedapp.domain.usecases

import com.example.watchedapp.data.models.config.ConfigResult
import com.example.watchedapp.data.repositories.config.ConfigRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetConfigUseCase @Inject constructor(
    private val configRepository: ConfigRepository,
) {
    operator fun invoke(): Flow<ConfigResult> = flow { emit(configRepository.getConfig()) }
}
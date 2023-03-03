package com.example.watchedapp.data.di

import com.example.watchedapp.data.repositories.config.ConfigRepository
import com.example.watchedapp.data.repositories.config.RemoteConfigRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsConfigRepository(
        configRepository: RemoteConfigRepository,
    ): ConfigRepository
}
package dev.martinloeseth.watchedapp.network.di

import dev.martinloeseth.watchedapp.network.ConfigNetworkDataSource
import dev.martinloeseth.watchedapp.network.MovieDetailsNetworkDataSource
import dev.martinloeseth.watchedapp.network.SearchNetworkDataSource
import dev.martinloeseth.watchedapp.network.retrofit.RetrofitConfigNetwork
import dev.martinloeseth.watchedapp.network.retrofit.RetrofitMovieDetailsNetwork
import dev.martinloeseth.watchedapp.network.retrofit.RetrofitSearchNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.martinloeseth.watchedapp.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun providesAuthInterceptor(): AuthInterceptor = AuthInterceptor(BuildConfig.API_KEY)

    @Provides
    @Singleton
    fun okHttpCallFactory(authInterceptor: AuthInterceptor): Call.Factory = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    @Provides
    @Singleton
    fun providesRetrofitConfigNetwork(
        networkJson: Json,
        okHttpCallFactory: Call.Factory,
    ): ConfigNetworkDataSource =
        RetrofitConfigNetwork(networkJson = networkJson, okHttpCallFactory = okHttpCallFactory)

    @Provides
    @Singleton
    fun providesRetrofitSearchNetwork(
        networkJson: Json,
        okHttpCallFactory: Call.Factory,
    ): SearchNetworkDataSource =
        RetrofitSearchNetwork(networkJson = networkJson, okHttpCallFactory = okHttpCallFactory)

    @Provides
    @Singleton
    fun providesRetrofitMovieDetailsNetwork(
        networkJson: Json,
        okHttpCallFactory: Call.Factory,
    ): MovieDetailsNetworkDataSource =
        RetrofitMovieDetailsNetwork(networkJson = networkJson, okHttpCallFactory = okHttpCallFactory)
}

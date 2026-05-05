package dev.martinloeseth.watchedapp.network.models

import dev.martinloeseth.watchedapp.domain.models.ImageConfig
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigNetworkModel(
    val images: ImagesNetworkModel,
    @SerialName("change_keys") val changeKeys: ArrayList<String> = arrayListOf(),
)

@Serializable
data class ImagesNetworkModel(
    @SerialName("base_url") val baseUrl: String,
    @SerialName("secure_base_url") val secureBaseUrl: String,
    @SerialName("backdrop_sizes") val backdropSizes: ArrayList<String> = arrayListOf(),
    @SerialName("logo_sizes") val logoSizes: ArrayList<String> = arrayListOf(),
    @SerialName("poster_sizes") val posterSizes: ArrayList<String> = arrayListOf(),
    @SerialName("profile_sizes") val profileSizes: ArrayList<String> = arrayListOf(),
    @SerialName("still_sizes") val stillSizes: ArrayList<String> = arrayListOf(),
)

fun ConfigNetworkModel.asImageConfig() = ImageConfig(
    baseUrl = images.baseUrl,
    secureBaseUrl = images.secureBaseUrl,
    backdropSizes = images.backdropSizes,
    logoSizes = images.logoSizes,
    posterSizes = images.posterSizes,
    profileSizes = images.profileSizes,
    stillSizes = images.stillSizes,
)

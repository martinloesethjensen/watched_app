package com.example.watchedapp.data.models.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigResult(
    val images: ImagesConfig,
    @SerialName("change_keys")
    val changeKeys: ArrayList<String> = arrayListOf()
)

@Serializable
data class ImagesConfig(
    @SerialName("base_url")
    val baseUrl: String,
    @SerialName("secure_base_url")
    val secureBaseUrl: String,
    @SerialName("backdrop_sizes")
    val backdropSizes : ArrayList<String> = arrayListOf(),
    @SerialName("logo_sizes")
    val logoSizes : ArrayList<String> = arrayListOf(),
    @SerialName("poster_sizes")
    val posterSizes : ArrayList<String> = arrayListOf(),
    @SerialName("profile_sizes")
    val profileSizes : ArrayList<String> = arrayListOf(),
    @SerialName("still_sizes")
    val stillSizes : ArrayList<String> = arrayListOf(),

)
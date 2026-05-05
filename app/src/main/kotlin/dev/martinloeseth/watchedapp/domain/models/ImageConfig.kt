package dev.martinloeseth.watchedapp.domain.models

data class ImageConfig(
    val baseUrl: String,
    val secureBaseUrl: String,
    val backdropSizes: ArrayList<String> = arrayListOf(),
    val logoSizes: ArrayList<String> = arrayListOf(),
    val posterSizes: ArrayList<String> = arrayListOf(),
    val profileSizes: ArrayList<String> = arrayListOf(),
    val stillSizes: ArrayList<String> = arrayListOf(),
)

package dev.martinloeseth.watchedapp.database.util

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class WatchlistConverter {
    @TypeConverter
    fun stringToListOfInt(value: String) = Json.decodeFromString<List<Int>>(value)

    @TypeConverter
    fun listOfIntToString(value: List<Int>) = Json.encodeToString(value)
}
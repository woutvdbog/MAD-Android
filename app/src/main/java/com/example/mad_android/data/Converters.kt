package com.example.mad_android.data

import androidx.room.TypeConverter
import com.example.mad_android.data.liveboard.dbDepartures
import com.example.mad_android.data.station.dbStationObject
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Room database type converters for converting custom data types to and from JSON strings.
 */
class Converters {

    /**
     * Converts a list of [dbStationObject] to a JSON string.
     *
     * @param value The list of [dbStationObject] to be converted.
     * @return The JSON string representation of the list.
     */
    @TypeConverter
    fun fromList(value: List<dbStationObject>): String {
        return Gson().toJson(value)
    }

    /**
     * Converts a JSON string to a list of [dbStationObject].
     *
     * @param value The JSON string to be converted.
     * @return The list of [dbStationObject] obtained from the JSON string.
     */
    @TypeConverter
    fun toList(value: String): List<dbStationObject> {
        val type = object : TypeToken<List<dbStationObject>>() {}.type
        return Gson().fromJson(value, type)
    }

    /**
     * Converts a [dbStationObject] to a JSON string.
     *
     * @param value The [dbStationObject] to be converted.
     * @return The JSON string representation of the object.
     */
    @TypeConverter
    fun fromDbStationObject(value: dbStationObject): String {
        return Gson().toJson(value)
    }

    /**
     * Converts a JSON string to a [dbStationObject].
     *
     * @param value The JSON string to be converted.
     * @return The [dbStationObject] obtained from the JSON string.
     */
    @TypeConverter
    fun toDbStationObject(value: String): dbStationObject {
        val type = object : TypeToken<dbStationObject>() {}.type
        return Gson().fromJson(value, type)
    }

    /**
     * Converts a [dbDepartures] object to a JSON string.
     *
     * @param value The [dbDepartures] object to be converted.
     * @return The JSON string representation of the object.
     */
    @TypeConverter
    fun fromDbDepartures(value: dbDepartures): String {
        return Gson().toJson(value)
    }

    /**
     * Converts a JSON string to a [dbDepartures] object.
     *
     * @param value The JSON string to be converted.
     * @return The [dbDepartures] object obtained from the JSON string.
     */
    @TypeConverter
    fun toDbDepartures(value: String): dbDepartures {
        val type = object : TypeToken<dbDepartures>() {}.type
        return Gson().fromJson(value, type)
    }
}
package com.example.mad_android.data

import androidx.room.TypeConverter
import com.example.mad_android.data.liveboard.dbDepartures
import com.example.mad_android.data.station.dbStationObject
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromList(value: List<dbStationObject>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toList(value: String): List<dbStationObject> {
        val type = object : TypeToken<List<dbStationObject>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromDbStationObject(value: dbStationObject): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toDbStationObject(value: String): dbStationObject {
        val type = object : TypeToken<dbStationObject>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromDbDepartures(value: dbDepartures): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toDbDepartures(value: String): dbDepartures {
        val type = object : TypeToken<dbDepartures>() {}.type
        return Gson().fromJson(value, type)
    }
}
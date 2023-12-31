package com.example.mad_android.data.station

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mad_android.model.Station

@Entity(tableName = "stations")
data class dbStation(
    @PrimaryKey
    val version: String,
    val timestamp: String,
)

fun dbStation.asDomainStation(): Station {
    return Station(
        version = version,
        timestamp = timestamp
    )
}

fun Station.asDbStation(): dbStation {
    return dbStation(
        version = version,
        timestamp = timestamp
    )
}

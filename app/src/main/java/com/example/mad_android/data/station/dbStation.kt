package com.example.mad_android.data.station

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mad_android.model.Station
import com.example.mad_android.model.StationObject

@Entity(tableName = "stations")
data class dbStation(
    @PrimaryKey
    val version: String,
    val timestamp: String,
    val station: List<dbStationObject> = emptyList()
)

data class dbStationObject(
    val locationX: String,
    val locationY: String,
    val id: String,
    val name: String,
    val link: String,
    val standardname: String
)

fun dbStation.asDomainStation(): Station {
    return Station(
        version = version,
        timestamp = timestamp,
        station = station.map {
            StationObject(
                locationX = it.locationX,
                locationY = it.locationY,
                id = it.id,
                name = it.name,
                link = it.link,
                standardname = it.standardname
            )
        }
    )
}

fun Station.asDbStation(): dbStation {
    return dbStation(
        version = version,
        timestamp = timestamp,
        station = station.map {
            dbStationObject(
                locationX = it.locationX,
                locationY = it.locationY,
                id = it.id,
                name = it.name,
                link = it.link,
                standardname = it.standardname
            )
        }
    )
}

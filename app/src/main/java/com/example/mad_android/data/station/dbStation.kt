package com.example.mad_android.data.station

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mad_android.model.Station
import com.example.mad_android.model.StationObject

/**
 * Database entity representing station information stored in the local Room database.
 *
 * @property version Version information for the station.
 * @property timestamp Timestamp indicating when the station data was retrieved.
 * @property station List of [dbStationObject] objects representing individual stations.
 */
@Entity(tableName = "stations")
data class dbStation(
    @PrimaryKey
    val version: String = "",
    val timestamp: String = "",
    val station: List<dbStationObject> = emptyList()
)

/**
 * Data class representing station information stored in the local Room database.
 *
 * @property locationX X-coordinate of the station's location.
 * @property locationY Y-coordinate of the station's location.
 * @property id Unique identifier for the station.
 * @property name Name of the station.
 * @property link Link to additional information about the station.
 * @property standardname Standard name of the station.
 */
data class dbStationObject(
    val locationX: String = "",
    val locationY: String = "",
    val id: String = "",
    val name: String = "",
    val link: String = "",
    val standardname: String = ""
)

/**
 * Converts a [dbStation] object to a [Station] domain object.
 *
 * @return Converted [Station] domain object.
 */
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

/**
 * Converts a [Station] domain object to a [dbStation] object for database storage.
 *
 * @return Converted [dbStation] object for database storage.
 */
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

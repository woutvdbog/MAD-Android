package com.example.mad_android.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents information about a group of stations.
 *
 * @property version Version information related to the station data.
 * @property timestamp Timestamp associated with the station data.
 * @property station List of stations as [StationObject].
 *
 * @constructor Creates a new instance of the [Station] data class.
 */
@Serializable
data class Station(
    @SerialName(value = "version")
    var version: String,
    @SerialName(value = "timestamp")
    var timestamp: String,
    @SerialName(value = "station")
    var station: List<StationObject>
)

/**
 * Represents information about an individual station.
 *
 * @property locationX X-coordinate of the station's location.
 * @property locationY Y-coordinate of the station's location.
 * @property id Unique identifier for the station.
 * @property name Display name of the station.
 * @property link Link associated with the station.
 * @property standardname Standard name associated with the station.
 *
 * @constructor Creates a new instance of the [StationObject] data class.
 */
@Serializable
data class StationObject(
    @SerialName(value = "locationX")
    var locationX: String,
    @SerialName(value = "locationY")
    var locationY: String,
    @SerialName(value = "id")
    var id: String,
    @SerialName(value = "name")
    var name: String,
    @SerialName(value = "@id")
    var link: String,
    @SerialName(value = "standardname")
    var standardname: String
)
package com.example.mad_android.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents liveboard information including departures from a specific station.
 *
 * @property version Version information related to the liveboard data.
 * @property timestamp Timestamp associated with the liveboard data.
 * @property station Name of the station for which the liveboard information is retrieved.
 * @property stationinfo Information about the station as a [StationObject].
 * @property departures List of departures from the station as a [Departures] object.
 *
 * @constructor Creates a new instance of the [Liveboard] data class.
 */
@Serializable
data class Liveboard(
    @SerialName(value = "version")
    var version: String,
    @SerialName(value = "timestamp")
    var timestamp: String,
    @SerialName(value = "station")
    var station: String,
    @SerialName(value = "stationinfo")
    var stationinfo: StationObject,
    @SerialName(value = "departures")
    var departures: Departures
)

/**
 * Represents information about a group of departures.
 *
 * @property number Amount of departures in the group.
 * @property departure List of individual departures as a [List] of [Departure].
 *
 * @constructor Creates a new instance of the [Departures] data class.
 */
@Serializable
data class Departures(
    @SerialName(value = "number")
    var number: Int,
    @SerialName(value = "departure")
    var departure: List<Departure>
)

/**
 * Represents information about an individual departure.
 *
 * @property id Unique identifier for the departure.
 * @property delay Delay in seconds for the departure.
 * @property station Name of the station for the departure.
 * @property stationinfo Information about the station as a [StationObject].
 * @property time Time of the departure.
 * @property vehicle Name of the vehicle for the departure.
 * @property vehicleinfo Information about the vehicle as a [Vehicle].
 * @property platform Platform information for the departure.
 * @property platforminfo Information about the platform as a [Platform].
 * @property canceled Flag indicating whether the departure is canceled.
 * @property left Flag indicating whether the departure has left.
 * @property isExtra Flag indicating whether the departure is extra.
 * @property departureConnection Connection information for the departure.
 *
 * @constructor Creates a new instance of the [Departure] data class.
 */
@Serializable
data class Departure(
    @SerialName(value = "id")
    var id: Int,
    @SerialName(value = "delay")
    var delay: Int,
    @SerialName(value = "station")
    var station: String,
    @SerialName(value = "stationinfo")
    var stationinfo: StationObject,
    @SerialName(value = "time")
    var time: String,
    @SerialName(value = "vehicle")
    var vehicle: String,
    @SerialName(value = "vehicleinfo")
    var vehicleinfo: Vehicle,
    @SerialName(value = "platform")
    var platform: String,
    @SerialName(value = "platforminfo")
    var platforminfo: Platform,
    @SerialName(value = "canceled")
    var canceled: Int,
    @SerialName(value = "left")
    var left: Int,
    @SerialName(value = "isExtra")
    var isExtra: Int,
    @SerialName(value = "departureConnection")
    var departureConnection: String
)


/**
 * Represents platform information.
 *
 * @property name Name of the platform.
 * @property normal Normal information about the platform.
 *
 * @constructor Creates a new instance of the [Platform] data class.
 */
@Serializable
data class Platform(
    @SerialName(value = "name")
    var name: String,
    @SerialName(value = "normal")
    var normal: String
)

/**
 * Represents information about a vehicle.
 *
 * @property name Name of the vehicle.
 * @property shortname Short name of the vehicle.
 * @property number Number associated with the vehicle.
 * @property type Type of the vehicle.
 * @property locationX X-coordinate of the vehicle's location.
 * @property locationY Y-coordinate of the vehicle's location.
 * @property link Link associated with the vehicle.
 *
 * @constructor Creates a new instance of the [Vehicle] data class.
 */
@Serializable
data class Vehicle(
    @SerialName(value = "name")
    var name: String,
    @SerialName(value = "shortname")
    var shortname: String,
    @SerialName(value = "number")
    var number: Int,
    @SerialName(value = "type")
    var type: String,
    @SerialName(value = "locationX")
    var locationX: String,
    @SerialName(value = "locationY")
    var locationY: String,
    @SerialName(value = "@id")
    var link: String
)
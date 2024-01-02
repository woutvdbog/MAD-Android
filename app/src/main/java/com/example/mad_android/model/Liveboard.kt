package com.example.mad_android.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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

@Serializable
data class Departures(
    @SerialName(value = "number")
    var number: Int,
    @SerialName(value = "departure")
    var departure: List<Departure>
)

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

@Serializable
data class Platform(
    @SerialName(value = "name")
    var name: String,
    @SerialName(value = "normal")
    var normal: String
)

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
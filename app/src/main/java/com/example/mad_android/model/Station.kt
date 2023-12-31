package com.example.mad_android.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Station(
    @SerialName(value = "version")
    var version: String,
    @SerialName(value = "timestamp")
    var timestamp: String,
    @SerialName(value = "station")
    var station: List<StationObject>
)

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
package com.example.mad_android.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
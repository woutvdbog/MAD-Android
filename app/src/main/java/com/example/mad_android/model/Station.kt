package com.example.mad_android.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Station(
    @SerialName(value = "version")
    var version: String,
    @SerialName(value = "timestamp")
    var timestamp: String
)
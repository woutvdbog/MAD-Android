package com.example.mad_android.model

import kotlinx.serialization.Serializable

/**
 * Represents a favorite station in the application.
 *
 * @property id Unique identifier for the favorite station
 * @property name Display name of the favorite station.
 * @property standardname Standard name associated with the favorite station.
 *
 * @constructor Creates a new instance of the [Favourite] data class.
 */
@Serializable
data class Favourite(
    val id: String,
    val name: String,
    val standardname: String
)
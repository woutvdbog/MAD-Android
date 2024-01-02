package com.example.mad_android.model

import kotlinx.serialization.Serializable

@Serializable
data class Favourite(
    val id: String,
    val name: String,
    val standardname: String
)
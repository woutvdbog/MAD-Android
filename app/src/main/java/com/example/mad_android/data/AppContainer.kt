package com.example.mad_android.data

import android.content.Context
import com.example.mad_android.data.station.CachingStationRepository
import com.example.mad_android.data.station.StationRepository
import com.example.mad_android.network.StationApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val stationRepository: StationRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    private val baseUrl = "http://api.irail.be/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    private val retrofitService: StationApiService by lazy {
        retrofit.create(StationApiService::class.java)
    }

    override val stationRepository: StationRepository by lazy {
        CachingStationRepository(
            StationDb.getDatabase(context).stationDao(),
            retrofitService,
            context
        )
    }
}
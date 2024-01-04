package com.example.mad_android.data

import android.content.Context
import com.example.mad_android.data.favourite.CachingFavouriteRepository
import com.example.mad_android.data.favourite.FavouriteRepository
import com.example.mad_android.data.liveboard.CachingLiveboardRepository
import com.example.mad_android.data.liveboard.LiveboardRepository
import com.example.mad_android.data.station.CachingStationRepository
import com.example.mad_android.data.station.StationRepository
import com.example.mad_android.network.LiveboardApiService
import com.example.mad_android.network.StationApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val stationRepository: StationRepository
    val liveboardRepository: LiveboardRepository
    val favouriteRepository: FavouriteRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    private val baseUrl = "http://api.irail.be/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    private val stationService: StationApiService by lazy {
        retrofit.create(StationApiService::class.java)
    }

    private val liveboardService: LiveboardApiService by lazy {
        retrofit.create(LiveboardApiService::class.java)
    }

    override val stationRepository: StationRepository by lazy {
        CachingStationRepository(
            StationDb.getDatabase(context).stationDao(),
            stationService,
        )
    }

    override val liveboardRepository: LiveboardRepository by lazy {
        CachingLiveboardRepository(
            StationDb.getDatabase(context).liveboardDao(),
            liveboardService
        )
    }

    override val favouriteRepository: FavouriteRepository by lazy {
        CachingFavouriteRepository(
            favouriteDao = StationDb.getDatabase(context).favouriteDao(),
        )
    }
}
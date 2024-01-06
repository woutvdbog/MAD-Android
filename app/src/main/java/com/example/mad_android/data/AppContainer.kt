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

/**
 * Dependency injection container for providing instances of repositories and services.
 */
interface AppContainer {
    val stationRepository: StationRepository
    val liveboardRepository: LiveboardRepository
    val favouriteRepository: FavouriteRepository
}

/**
 * Default implementation of the [AppContainer] interface for dependency injection.
 *
 * @param context The application context.
 */
class DefaultAppContainer(private val context: Context) : AppContainer {
    // Base URL for the API
    private val baseUrl = "http://api.irail.be/"

    // Retrofit instance for network requests
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    // Service for fetching station information from the API
    private val stationService: StationApiService by lazy {
        retrofit.create(StationApiService::class.java)
    }

    // Service for fetching liveboard information from the API
    private val liveboardService: LiveboardApiService by lazy {
        retrofit.create(LiveboardApiService::class.java)
    }

    // Repository for managing station information
    override val stationRepository: StationRepository by lazy {
        CachingStationRepository(
            TrainAppDb.getDatabase(context).stationDao(),
            stationService,
        )
    }

    // Repository for managing liveboard information
    override val liveboardRepository: LiveboardRepository by lazy {
        CachingLiveboardRepository(
            TrainAppDb.getDatabase(context).liveboardDao(),
            liveboardService
        )
    }

    // Repository for managing favourite information
    override val favouriteRepository: FavouriteRepository by lazy {
        CachingFavouriteRepository(
            favouriteDao = TrainAppDb.getDatabase(context).favouriteDao(),
        )
    }
}
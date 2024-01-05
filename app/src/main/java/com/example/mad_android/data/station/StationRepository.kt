package com.example.mad_android.data.station

import android.util.Log
import com.example.mad_android.model.Station
import com.example.mad_android.network.StationApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repository interface for managing station information.
 */
interface StationRepository {
    /**
     * Retrieves station information from the repository as a Flow.
     *
     * @return A [Flow] emitting a [Station] object.
     */
    fun getStations(): Flow<Station>

    /**
     * Refreshes station information by making a network request and updating the local database.
     */
    suspend fun refresh()
}

/**
 * Implementation of the [StationRepository] interface that caches station information
 * using a local Room database and fetches data from a remote API when needed.
 *
 * @param stationDao The Data Access Object (DAO) for station information.
 * @param stationApiService The API service for fetching station information.
 */
class CachingStationRepository (
    private val stationDao: StationDao,
    private val stationApiService: StationApiService,
) : StationRepository {
    /**
     * Retrieves station information from the local Room database, mapping it to a domain model.
     *
     * @return A [Flow] emitting a [Station] domain object.
     */
    override fun getStations(): Flow<Station> {
        return stationDao.getAll().map {
            it?.asDomainStation() ?: Station("","", emptyList())
        }
    }

    /**
     * Refreshes station information by making a network request and updating the local database.
     */
    override suspend fun refresh() {
        try {
            stationApiService.getStations().also {
                externalStations -> stationDao.insertAll(externalStations.asDbStation())
            }
        } catch (e: Exception) {
            Log.d("StationRepository", "refresh: $e")
        }
    }
}
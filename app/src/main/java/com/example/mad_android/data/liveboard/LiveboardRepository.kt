package com.example.mad_android.data.liveboard

import android.util.Log
import com.example.mad_android.model.Liveboard
import com.example.mad_android.network.LiveboardApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repository interface for managing liveboard information.
 */
interface LiveboardRepository {

    /**
     * Retrieves liveboard information for a specific station from the repository as a Flow.
     *
     * @param stationId The unique identifier of the station.
     * @return A [Flow] emitting a [Liveboard] object for the specified station.
     */
    fun getLiveboard(stationId: String): Flow<Liveboard>

    /**
     * Refreshes liveboard information for a specific station by making a network request
     * and updating the local database.
     *
     * @param stationId The unique identifier of the station.
     */
    suspend fun refresh(stationId: String)
}

/**
 * Implementation of the [LiveboardRepository] interface that caches liveboard information
 * using a local Room database and fetches data from a remote API when needed.
 *
 * @param liveboardDao The Data Access Object (DAO) for liveboard information.
 * @param liveboardApiService The API service for fetching liveboard information.
 */
class CachingLiveboardRepository(
    private val liveboardDao: LiveboardDao,
    private val liveboardApiService: LiveboardApiService,
) : LiveboardRepository {

    /**
     * Retrieves liveboard information for a specific station from the local Room database,
     * mapping it to a domain model.
     *
     * @param stationId The unique identifier of the station.
     * @return A [Flow] emitting a [Liveboard] domain object for the specified station.
     */
    override fun getLiveboard(stationId: String): Flow<Liveboard> {
        return liveboardDao.getLiveboard().map {
            it.asDomainLiveboard()
        }
    }

    /**
     * Refreshes liveboard information for a specific station by making a network request
     * and updating the local database.
     *
     * @param stationId The unique identifier of the station.
     */
    override suspend fun refresh(stationId: String) {
        try {
            liveboardApiService.getLiveboard(stationId).also {
                liveboardDao.insertAll(it.asDbLiveboard())
            }
        } catch (e: Exception) {
            Log.d("LiveboardRepository", "refresh: $e")
        }
    }
}
package com.example.mad_android.data.station

import android.util.Log
import com.example.mad_android.model.Station
import com.example.mad_android.network.StationApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface StationRepository {
    fun getStations(): Flow<Station>

    suspend fun refresh()
}

class CachingStationRepository (
    private val stationDao: StationDao,
    private val stationApiService: StationApiService,
) : StationRepository {
    override fun getStations(): Flow<Station> {
        return stationDao.getAll().map {
            it?.asDomainStation() ?: Station("","", emptyList())
        }
    }

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
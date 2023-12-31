package com.example.mad_android.data.station

import android.content.Context
import android.util.Log
import com.example.mad_android.model.Station
import com.example.mad_android.network.StationApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

interface StationRepository {
    fun getStations(): Flow<Station>

    suspend fun refresh()
}

class CachingStationRepository (
    private val stationDao: StationDao,
    private val stationApiService: StationApiService,
    context: Context
) : StationRepository {
    override fun getStations(): Flow<Station> {
        // convert single dbStation to list of Stations
        return stationDao.getAll().map {
            it?.asDomainStation() ?: Station("0", "0")
        }.onEach {
            Log.d("CachingStationRepository", "getStations: $it")
        }
    }

    override suspend fun refresh() {
        stationApiService.getStations().also {
            externalStations -> stationDao.insertAll(externalStations.asDbStation())
        }
    }
}
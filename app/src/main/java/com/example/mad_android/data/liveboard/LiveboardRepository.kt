package com.example.mad_android.data.liveboard

import android.content.Context
import android.util.Log
import com.example.mad_android.model.Liveboard
import com.example.mad_android.network.LiveboardApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

interface LiveboardRepository {
    fun getLiveboard(stationId: String): Flow<Liveboard>

    suspend fun refresh(stationId: String)
}

class CachingLiveboardRepository(
    private val liveboardDao: LiveboardDao,
    private val liveboardApiService: LiveboardApiService,
    Context: Context
) : LiveboardRepository {
    override fun getLiveboard(stationId: String): Flow<Liveboard> {
        return liveboardDao.getLiveboard().map {
            Log.d("CachingStationRepository",  "getLiveboard: $it")
            it.asDomainLiveboard()
        }.onEach {
            Log.d("CachingStationRepository",  "getLiveboard: $it")
        }
    }

    override suspend fun refresh(stationId: String) {
        liveboardApiService.getLiveboard(stationId).also {
            liveboardDao.insertAll(it.asDbLiveboard())
        }
    }
}
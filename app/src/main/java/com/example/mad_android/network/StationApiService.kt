package com.example.mad_android.network
import android.util.Log
import com.example.mad_android.model.Station
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET
import retrofit2.http.Query

interface StationApiService {
    @GET("stations")
    suspend fun getStations(@Query("format") format: String = "json"): Station
}

fun StationApiService.getStationsAsFlow() : Flow<Station> = flow {
    try {
        emit(getStations())
    }
    catch (e: Exception) {
        Log.e("StationApiService", "getStationsAsFlow: ${e.message}")
    }
}
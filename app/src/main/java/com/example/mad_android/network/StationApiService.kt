package com.example.mad_android.network
import android.util.Log
import com.example.mad_android.model.Station
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface defining the API service for station-related network requests.
 *
 * This interface declares a method for retrieving station data from the API.
 */
interface StationApiService {
    /**
     * Retrieves station data from the API.
     *
     * @param format Format for the response data (default is "json").
     * @return [Station] object containing information about stations.
     */
    @GET("stations")
    suspend fun getStations(@Query("format") format: String = "json"): Station
}

/**
 * Extension function for [StationApiService] to retrieve stations as a flow.
 *
 * This function wraps the `getStations` method call in a flow, allowing the result to be observed as a flow.
 *
 * @return [Flow] of [Station] containing information about stations.
 */
fun StationApiService.getStationsAsFlow() : Flow<Station> = flow {
    try {
        emit(getStations())
    }
    catch (e: Exception) {
        Log.e("StationApiService", "getStationsAsFlow: ${e.message}")
    }
}
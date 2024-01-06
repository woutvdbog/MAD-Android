package com.example.mad_android.network
import com.example.mad_android.model.Station
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
package com.example.mad_android.network

import com.example.mad_android.model.Liveboard
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface defining the API service for liveboard-related network requests.
 *
 * This interface declares methods for retrieving liveboard data from the API.
 */
interface LiveboardApiService {
    /**
     * Retrieves liveboard data for a specific station from the API.
     *
     * @param stationId Unique identifier for the station.
     * @param format Format for the response data (default is "json").
     * @return [Liveboard] object containing liveboard information for the specified station.
     */
    @GET("liveboard")
    suspend fun getLiveboard(@Query("id") stationId: String, @Query("format") format: String = "json"): Liveboard
}


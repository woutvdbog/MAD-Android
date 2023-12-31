package com.example.mad_android.network

import com.example.mad_android.model.Liveboard
import retrofit2.http.GET
import retrofit2.http.Query

interface LiveboardApiService {
    @GET("liveboard")
    suspend fun getLiveboard(@Query("id") stationId: String, @Query("format") format: String = "json"): Liveboard
}


package com.example.mad_android.repository

import com.example.mad_android.data.station.CachingStationRepository
import com.example.mad_android.data.station.StationDao
import com.example.mad_android.data.station.asDomainStation
import com.example.mad_android.data.station.dbStation
import com.example.mad_android.data.station.dbStationObject
import com.example.mad_android.network.StationApiService
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class StationRepositoryTest {
    private lateinit var stationDao: StationDao
    private lateinit var stationApiService: StationApiService
    private lateinit var cachingStationRepository: CachingStationRepository

    private val mockDbStations =
        dbStation("1", "1", listOf(
            dbStationObject("1", "1", "1", "TestStation", "1", "1"),
            dbStationObject("2", "2", "2", "TestStation2", "2", "2")
        ))

    @Before
    fun setup() {
        stationDao = mock(StationDao::class.java)
        stationApiService = mock(StationApiService::class.java)
        cachingStationRepository = CachingStationRepository(stationDao, stationApiService)
    }

    @Test
    fun testGetStations() = runBlocking {
        `when`(stationDao.getAll()).thenReturn(flowOf(mockDbStations))

        val result = cachingStationRepository.getStations()

        result.collect { stations ->
            assertEquals(mockDbStations.asDomainStation(), stations)
        }
    }

    @Test
    fun testGetStations_returnsEmptyList() = runBlocking {
        val mockDbStations = dbStation("1", "1", emptyList())

        `when`(stationDao.getAll()).thenReturn(flowOf(mockDbStations))

        val result = cachingStationRepository.getStations()

        result.collect { stations ->
            assertEquals(0, stations.station.size)
            assertEquals(mockDbStations.asDomainStation(), stations)
        }
    }

    @Test
    fun testRefresh() = runBlocking {
        `when`(stationApiService.getStations()).thenReturn(mockDbStations.asDomainStation())

        cachingStationRepository.refresh()

        verify(stationDao).insertAll(mockDbStations)
    }


}
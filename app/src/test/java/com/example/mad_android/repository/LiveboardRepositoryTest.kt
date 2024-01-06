package com.example.mad_android.repository

import com.example.mad_android.data.liveboard.CachingLiveboardRepository
import com.example.mad_android.data.liveboard.LiveboardDao
import com.example.mad_android.data.liveboard.asDomainLiveboard
import com.example.mad_android.data.liveboard.dbDeparture
import com.example.mad_android.data.liveboard.dbDepartures
import com.example.mad_android.data.liveboard.dbLiveboard
import com.example.mad_android.data.liveboard.dbVehicle
import com.example.mad_android.data.station.dbStationObject
import com.example.mad_android.model.Platform
import com.example.mad_android.network.LiveboardApiService
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class LiveboardRepositoryTest {

    private lateinit var liveboardDao: LiveboardDao
    private lateinit var liveboardApiService: LiveboardApiService
    private lateinit var cachingLiveboardRepository: CachingLiveboardRepository

    private val mockDbLiveboard = dbLiveboard(
        "1.2",
        "1704315149",
        "Ghent-Sint-Pieters",
        dbStationObject(
            "3.710675",
            "51.035896",
            "BE.NMBS.008892007",
            "Ghent-Sint-Pieters",
            "http://irail.be/stations/NMBS/008892007",
            "Gent-Sint-Pieters"
        ),
        dbDepartures(
            1,
            List(
                1
            ) {
                dbDeparture(
                    0,
                    0,
                    "De Panne",
                    dbStationObject(
                        "2.601963",
                        "51.0774",
                        "BE.NMBS.008892338",
                        "De Panne",
                        "http://irail.be/stations/NMBS/008892338",
                        "De Panne"
                    ),
                    "1704315120",
                    "BE.NMBS.IC3020",
                    dbVehicle(
                        "BE.NMBS.IC3020",
                        "IC 3020",
                        3020,
                        "IC",
                        "0",
                        "0",
                        "http://irail.be/vehicle/IC3020"
                    ),
                    "7",
                    Platform(
                        "7",
                        "7",
                    ),
                    0,
                    0,
                    0,
                    "http://irail.be/connections/8892007/20240103/IC 3020"
                )
            }
        )
    )
    @Before
    fun setup() {
        liveboardDao = mock(LiveboardDao::class.java)
        liveboardApiService = mock(LiveboardApiService::class.java)
        cachingLiveboardRepository = CachingLiveboardRepository(liveboardDao, liveboardApiService)
    }

    @Test
    fun getLiveboard() = runBlocking {

        `when`(liveboardDao.getLiveboard()).thenReturn(flowOf(mockDbLiveboard))

        val result = cachingLiveboardRepository.getLiveboard("test")

        result.collect { liveboards ->
            assertEquals(mockDbLiveboard.asDomainLiveboard(), liveboards)
        }
    }

    @Test
    fun testGetLiveboard_returnsEmptyList() = runBlocking {
        val emptyMockDbLiveboard = dbLiveboard(
            "1.2",
            "1704315149",
            "Ghent-Sint-Pieters",
            dbStationObject(
                "3.710675",
                "51.035896",
                "BE.NMBS.008892007",
                "Ghent-Sint-Pieters",
                "http://irail.be/stations/NMBS/008892007",
                "Gent-Sint-Pieters"
            ),
            dbDepartures(
                1,
                emptyList()
            )
        )

        `when`(liveboardDao.getLiveboard()).thenReturn(flowOf(emptyMockDbLiveboard))

        val result = cachingLiveboardRepository.getLiveboard("test")

        result.collect { liveboard ->
            assertEquals(0, liveboard.departures.departure.size)
            assertEquals(emptyMockDbLiveboard.asDomainLiveboard(), liveboard)
        }
    }

    @Test
    fun testRefresh() = runBlocking {
        `when`(liveboardApiService.getLiveboard("test")).thenReturn(mockDbLiveboard.asDomainLiveboard())

        cachingLiveboardRepository.refresh("test")

        Mockito.verify(liveboardDao).insertAll(mockDbLiveboard)
    }
}
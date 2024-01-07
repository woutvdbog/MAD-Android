package com.example.mad_android.viewmodel

import com.example.mad_android.data.liveboard.LiveboardRepository
import com.example.mad_android.model.Departure
import com.example.mad_android.model.Departures
import com.example.mad_android.model.Liveboard
import com.example.mad_android.model.Platform
import com.example.mad_android.model.StationObject
import com.example.mad_android.model.Vehicle
import com.example.mad_android.ui.screens.schedule.LiveboardUiState
import com.example.mad_android.ui.screens.schedule.LiveboardViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class LiveboardViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var liveboardRepository: LiveboardRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getLiveboard_success() = runBlocking {
        `when`(liveboardRepository.refresh("BE.NMBS.008892007")).thenReturn(Unit)
        `when`(liveboardRepository.getLiveboard("BE.NMBS.008892007")).thenReturn(
            flowOf(
                mockLiveboard()
            )
        )

        val liveboardViewModel = LiveboardViewModel(liveboardRepository)

        runBlocking {
            liveboardViewModel.getLiveboard("BE.NMBS.008892007")
        }

        verify(liveboardRepository).getLiveboard("BE.NMBS.008892007")

        assert(liveboardViewModel.liveboardUiState is LiveboardUiState.Success)
    }

    @Test
    fun getLiveboard_stationIdNotMatching_error() = runBlocking {
        `when`(liveboardRepository.refresh("BE.NMBS.008892007")).thenReturn(Unit)
        `when`(liveboardRepository.getLiveboard("BE.NMBS.008892007")).thenReturn(
            flowOf(
                mockLiveboard()
            )
        )

        val liveboardViewModel = LiveboardViewModel(liveboardRepository)

        runBlocking {
            liveboardViewModel.getLiveboard("BE.NMBS.008892008")
        }

        verify(liveboardRepository).getLiveboard("BE.NMBS.008892008")

        assert(liveboardViewModel.liveboardUiState is LiveboardUiState.Error)
    }

    @Test
    fun getLiveboard_error(): Unit = runTest {
        `when`(liveboardRepository.getLiveboard("BE.NMBS.008892007")).thenThrow(RuntimeException("Test exception"))

        val liveboardViewModel = LiveboardViewModel(liveboardRepository)

        assert(liveboardViewModel.liveboardUiState is LiveboardUiState.Error)
    }

    private fun mockLiveboard(): Liveboard {
        return Liveboard(
            "1.2",
            "1704315149",
            "Ghent-Sint-Pieters",
            StationObject(
                "3.710675",
                "51.035896",
                "BE.NMBS.008892007",
                "Ghent-Sint-Pieters",
                "http://irail.be/stations/NMBS/008892007",
                "Gent-Sint-Pieters"
            ),
            Departures(
                1,
                List(
                    1
                ) {
                    Departure(
                        0,
                        0,
                        "De Panne",
                        StationObject(
                            "2.601963",
                            "51.0774",
                            "BE.NMBS.008892338",
                            "De Panne",
                            "http://irail.be/stations/NMBS/008892338",
                            "De Panne"
                        ),
                        "1704315120",
                        "BE.NMBS.IC3020",
                        Vehicle(
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
    }
}
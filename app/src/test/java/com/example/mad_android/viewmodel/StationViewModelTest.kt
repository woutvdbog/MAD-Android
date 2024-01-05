package com.example.mad_android.viewmodel

import com.example.mad_android.data.station.StationRepository
import com.example.mad_android.ui.screens.stations.StationUiState
import com.example.mad_android.ui.screens.stations.StationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class StationViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var stationRepository: StationRepository

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
    fun getStations_Loading() {
        val stationViewModel = StationViewModel(stationRepository)

        assert(stationViewModel.stationUiState is StationUiState.Loading)
    }

    @Test
    fun getStations_Success() {
        val stationViewModel = StationViewModel(stationRepository)

        stationViewModel.getStations()

        verify(stationRepository, times(2)).getStations()

        assert(stationViewModel.stationUiState is StationUiState.Success)
    }

    @Test
    fun getStations_Error(): Unit = runTest {

        `when`(stationRepository.getStations()).thenThrow(RuntimeException("Test exception"))

        val stationViewModel = StationViewModel(stationRepository)

        assert(stationViewModel.stationUiState is StationUiState.Error)
    }
}
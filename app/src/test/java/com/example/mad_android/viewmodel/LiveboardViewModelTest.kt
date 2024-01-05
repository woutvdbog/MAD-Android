package com.example.mad_android.viewmodel

import com.example.mad_android.data.liveboard.LiveboardRepository
import com.example.mad_android.ui.screens.schedule.LiveboardUiState
import com.example.mad_android.ui.screens.schedule.LiveboardViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
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
    fun getLiveboard_Success() {
        val liveboardViewModel = LiveboardViewModel(liveboardRepository)

        liveboardViewModel.getLiveboard("test")

        verify(liveboardRepository).getLiveboard("test")

        assert(liveboardViewModel.liveboardUiState is LiveboardUiState.Success)
    }
}
package com.example.mad_android.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mad_android.TrainApplication
import com.example.mad_android.data.station.StationRepository
import com.example.mad_android.model.Station
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


sealed interface StationUiState {
    data class Success(val stations: Station) : StationUiState

    data class Error(val errorMessage: String) : StationUiState

    object Loading : StationUiState
}

data class StationState(val station: Station = Station("","", emptyList()))

class StationViewModel(
    private val stationRepository: StationRepository
) : ViewModel() {
    private var _uiState : StationUiState by mutableStateOf(StationUiState.Loading)
        private set

    private val _stations = MutableStateFlow<List<Station>>(emptyList())
    val stations : StateFlow<List<Station>> get() = _stations

    lateinit var uiListState : StateFlow<StationState>

    init {
        getStations()
    }

    private fun getStations() {
        try {
            viewModelScope.launch { stationRepository.refresh() }

            uiListState = stationRepository.getStations().map {
                StationState(it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = StationState()
            )

            _uiState = StationUiState.Success(uiListState.value.station)


        } catch (
            e: Exception
        ) {
            Log.e("StationViewModel", "getStations: ${e.message}")
            _uiState = StationUiState.Error(e.message ?: "Unknown error")
        }
    }

    companion object {
        private var Instance: StationViewModel? = null
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                if(Instance == null) {
                    val application = (this[APPLICATION_KEY] as TrainApplication)
                    val stationRepository = application.container.stationRepository
                    Instance = StationViewModel(stationRepository = stationRepository)
                }
                Instance!!
            }
        }
    }
}
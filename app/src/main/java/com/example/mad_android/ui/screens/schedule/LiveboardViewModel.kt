package com.example.mad_android.ui.screens.schedule

import android.annotation.SuppressLint
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
import com.example.mad_android.data.liveboard.LiveboardRepository
import com.example.mad_android.model.Departures
import com.example.mad_android.model.Liveboard
import com.example.mad_android.model.StationObject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

/**
 * Sealed interface representing the different states of the Liveboard screen.
 */
sealed interface LiveboardUiState {
    data class Success(val schedule: Liveboard) : LiveboardUiState

    data class Error(val errorMessage: String) : LiveboardUiState

    object Loading : LiveboardUiState
}

/**
 * Data class representing the state of the Liveboard.
 * @property liveboard Liveboard object containing schedule and departure information.
 */
data class LiveboardState(
    val liveboard: Liveboard = Liveboard(
        "",
        "",
        "",
        StationObject(
            "",
            "",
            "",
            "",
            "",
            "",
        ),
        Departures(
            0,
            emptyList()
        ),
    ))

/**
 * ViewModel for the Liveboard screen.
 * @property liveboardRepository The repository responsible for handling Liveboard data.
 */
class LiveboardViewModel(
    private val liveboardRepository: LiveboardRepository
) : ViewModel() {
    private var _uiState : LiveboardUiState = LiveboardUiState.Loading

    val liveboardUiState : LiveboardUiState
        get() = _uiState

    lateinit var uiListState : StateFlow<LiveboardState>

    var isRefreshing by mutableStateOf(false)

    /**
     * Initializes the ViewModel and triggers the retrieval of Liveboard information.
     */
    init {
        getLiveboard("Gent-Sint-Pieters")
    }

    /**
     * Retrieves the Liveboard information for the specified station.
     * Updates the UI state accordingly.
     * @param station The station for which the Liveboard information is to be retrieved.
     */
    fun getLiveboard(station: String) {
            viewModelScope.launch {
                _uiState = LiveboardUiState.Loading
                try {
                    liveboardRepository.refresh(station)

                    val liveboardResult = liveboardRepository.getLiveboard(station)

                    if(liveboardResult.first().stationinfo.id != station) {
                        _uiState = LiveboardUiState.Error("Error while fetching liveboard")
                        return@launch
                    }

                    uiListState = liveboardResult.map {
                        LiveboardState(formatLiveboard(it))
                    }.stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(),
                        initialValue = LiveboardState()
                    )

                    _uiState = LiveboardUiState.Success(uiListState.value.liveboard)
                } catch (e: Exception) {
                    _uiState = LiveboardUiState.Error(e.message ?: "An unknown error occured")
                }
            }
    }

    /**
     * Formats the departure times in the Liveboard to HH:mm format.
     * @param liveboard The original Liveboard object.
     * @return Liveboard object with formatted departure times.
     */
    @SuppressLint("SimpleDateFormat")
    private fun formatLiveboard(liveboard: Liveboard): Liveboard {
        val sdf = SimpleDateFormat("HH:mm")
        liveboard.departures.departure.forEach {
            val netDate = Date(it.time.toLong() * 1000)
            sdf.timeZone = TimeZone.getTimeZone("Europe/Brussels")
            val time = sdf.format(netDate)
            it.time = time
        }

        return liveboard
    }

    companion object {
        private var Instance: LiveboardViewModel? = null
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                if(Instance == null) {
                    val application = (this[APPLICATION_KEY] as TrainApplication)
                    val liveboardRepository = application.container.liveboardRepository
                    Instance = LiveboardViewModel(liveboardRepository = liveboardRepository)
                }
                Instance!!
            }
        }
    }
}
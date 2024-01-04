package com.example.mad_android.ui.screens.schedule

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
import com.example.mad_android.data.liveboard.LiveboardRepository
import com.example.mad_android.model.Departures
import com.example.mad_android.model.Liveboard
import com.example.mad_android.model.StationObject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

sealed interface LiveboardUiState {
    data class Success(val schedule: Liveboard) : LiveboardUiState

    data class Error(val errorMessage: String) : LiveboardUiState

    object Loading : LiveboardUiState
}

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


class LiveboardViewModel(
    private val liveboardRepository: LiveboardRepository
) : ViewModel() {
    private var _uiState : LiveboardUiState = LiveboardUiState.Loading
        private set

    val liveboardUiState : LiveboardUiState
        get() = _uiState

    lateinit var uiListState : StateFlow<LiveboardState>

    var isRefreshing by mutableStateOf(false)
        private set

    init {
        getLiveboard("Gent-Sint-Pieters")
    }

    fun getLiveboard(station: String) {
        _uiState = LiveboardUiState.Loading
        try {
            viewModelScope.launch {
                liveboardRepository.refresh(station)

                uiListState = liveboardRepository.getLiveboard(station).map {
                    LiveboardState(formatLiveboard(it))
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(),
                    initialValue = LiveboardState()
                )

                _uiState = LiveboardUiState.Success(uiListState.value.liveboard)
            }
        } catch (e: Exception) {
            Log.d("probleem", "getLiveboard: ${e.message}")
            _uiState = LiveboardUiState.Error(e.message ?: "An unknown error occured")
        }
    }

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
        val Factory: ViewModelProvider.Factory = viewModelFactory(

        ) {
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
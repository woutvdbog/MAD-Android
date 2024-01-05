package com.example.mad_android.ui.screens.favourites

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mad_android.TrainApplication
import com.example.mad_android.data.favourite.FavouriteRepository
import com.example.mad_android.model.Favourite
import com.example.mad_android.model.StationObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed interface FavouriteUiState {
    data class Success(val favourites: List<Favourite>) : FavouriteUiState

    data class Error(val errorMessage: String) : FavouriteUiState

    object Loading : FavouriteUiState
}

data class FavouriteState(val favourite: Favourite = Favourite("","", ""))

class FavouriteViewModel(
    private val favouriteRepository: FavouriteRepository
) : ViewModel() {
    private var _uiState : FavouriteUiState by mutableStateOf(FavouriteUiState.Loading)
        private set
    val uiState : FavouriteUiState get() = _uiState

    private val _favourites = MutableStateFlow<List<Favourite>>(emptyList())
    val favourites: StateFlow<List<Favourite>> get() = _favourites

    init {
        getFavourites()
    }

    fun getFavourites() {
        _uiState = FavouriteUiState.Loading
        viewModelScope.launch {
            try {

                favouriteRepository.getFavourites().collect {
                    _favourites.value = it
                }

                _uiState = FavouriteUiState.Success(favourites.value)

            } catch (e: Exception) {
                _uiState = FavouriteUiState.Error("Error loading favourites")
            }
        }
    }

    fun addFavourite(station: StationObject) {
        viewModelScope.launch {
            favouriteRepository.addFavourite(station)
        }
    }

    fun removeFavourite(favourite: Favourite) {
        viewModelScope.launch {
            favouriteRepository.removeFavourite(favourite)
        }
    }

    companion object {
        private var Instance: FavouriteViewModel? = null
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                if(Instance == null) {
                    val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TrainApplication)
                    val favouriteRepository = application.container.favouriteRepository
                    Instance = FavouriteViewModel(favouriteRepository = favouriteRepository)
                }
                Instance!!
            }
        }
    }
}
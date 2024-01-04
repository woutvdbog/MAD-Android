package com.example.mad_android.ui.screens.stations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mad_android.ui.components.Loading
import com.example.mad_android.ui.screens.favourites.FavouriteViewModel
import com.example.mad_android.ui.screens.stations.components.StationCard

@Composable
fun StationsScreen(
    stationViewModel: StationViewModel = viewModel(factory = StationViewModel.Factory),
    favouriteViewModel: FavouriteViewModel = viewModel(factory = FavouriteViewModel.Factory),
    onStationSelected : (String) -> Unit
) {
    when(stationViewModel.stationUiState) {
        is StationUiState.Loading -> {
            Loading()
        }
        is StationUiState.Error -> {
            Text(text = "Er deed zich een error voor")
        }
        is StationUiState.Success -> {
            StationsScreenComponent(
                stationViewModel = stationViewModel,
                favouriteViewModel = favouriteViewModel,
                onStationSelected = onStationSelected
            )
        }

    }
}

@Composable
fun StationsScreenComponent(
    stationViewModel: StationViewModel = viewModel(factory = StationViewModel.Factory),
    favouriteViewModel: FavouriteViewModel = viewModel(factory = FavouriteViewModel.Factory),
    onStationSelected : (String) -> Unit
) {
    val stationListState = stationViewModel.uiListState.collectAsState()

    val searchText = stationViewModel.searchText.collectAsState()
    val lazyListState = rememberLazyListState()

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = searchText.value,
                onValueChange = { stationViewModel.onSeachTextChange(it) },
                label = { Text(text = "Zoeken") },
                maxLines = 1,
                modifier = Modifier
                    .testTag("SearchBar")
                    .fillMaxWidth()
                    .padding(4.dp)
            )
            LazyColumn(state = lazyListState) {
                items(stationListState.value.station.station.size) { index ->
                    StationCard(
                        favouriteViewModel = favouriteViewModel,
                        station = stationListState.value.station.station[index],
                        onStationSelected = onStationSelected
                    )
                }
            }
        }
    }
}
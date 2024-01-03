package com.example.mad_android.ui.screens.schedule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mad_android.ui.screens.schedule.components.DepartureCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    liveboardViewModel: LiveboardViewModel = viewModel(factory = LiveboardViewModel.Factory),
    modifier: Modifier = Modifier,
    navController: NavController,
    station : String?
) {
    Column(
        modifier = modifier.fillMaxSize()
    ){
    when(liveboardViewModel.liveboardUiState) {
            is LiveboardUiState.Loading -> {
                Text(text = "Loading...")
            }

            is LiveboardUiState.Error -> {
                Text(text = "Er deed zich een error voor")
            }

            is LiveboardUiState.Success -> {
                ScheduleScreenComponent(
                    liveboardViewModel = liveboardViewModel,
                    modifier = modifier,
                    navController = navController,
                    station = station
                )
            }
        }
    }

    LaunchedEffect(true) {
        liveboardViewModel.getLiveboard(station!!)
    }
}

@Composable
fun ScheduleScreenComponent(
    liveboardViewModel: LiveboardViewModel = viewModel(factory = LiveboardViewModel.Factory),
    modifier: Modifier = Modifier,
    navController: NavController,
    station : String?
) {
    val liveboardListState = liveboardViewModel.uiListState.collectAsState()
    val lazyListState = rememberLazyListState()

    LazyColumn(
        state = lazyListState,
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        liveboardListState.value.liveboard.departures.departure.forEach { departure ->
            item {
                DepartureCard(
                    modifier = modifier,
                    departure = departure
                )
            }
        }
    }
}
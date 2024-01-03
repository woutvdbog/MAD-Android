package com.example.mad_android.ui.screens.schedule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
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
    val liveboardListState = liveboardViewModel.uiListState.collectAsState()
    val lazyListState = rememberLazyListState()

    Column(
        modifier = modifier.fillMaxSize()
    ){
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

    LaunchedEffect(true) {
        liveboardViewModel.getLiveboard(station!!)
    }
}
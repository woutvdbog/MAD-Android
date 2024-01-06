package com.example.mad_android.ui.screens.schedule

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mad_android.ui.components.Error
import com.example.mad_android.ui.components.Loading
import com.example.mad_android.ui.screens.schedule.components.DepartureCard

/**
 * Composable function for the Schedule screen.
 * @param modifier Modifier for customizing the appearance of the ScheduleScreen.
 * @param liveboardViewModel ViewModel for handling Liveboard data.
 * @param station The station for which the Liveboard information is to be displayed.
 */
@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    liveboardViewModel: LiveboardViewModel = viewModel(factory = LiveboardViewModel.Factory),
    station : String?
) {
    Column(
        modifier = modifier.fillMaxSize()
    ){
    when(liveboardViewModel.liveboardUiState) {
            is LiveboardUiState.Loading -> {
                Loading()
            }

            is LiveboardUiState.Error -> {
                Error()
            }

            is LiveboardUiState.Success -> {
                ScheduleScreenComponent(
                    liveboardViewModel = liveboardViewModel,
                    modifier = modifier,
                    station = station
                )
            }
        }
    }

    LaunchedEffect(true) {
        liveboardViewModel.getLiveboard(station!!)
    }
}

/**
 * Composable function for the main content of the Schedule screen.
 * @param modifier Modifier for customizing the appearance of the ScheduleScreenComponent.
 * @param liveboardViewModel ViewModel for handling Liveboard data.
 * @param station The station for which the Liveboard information is to be displayed.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScheduleScreenComponent(
    modifier: Modifier = Modifier,
    liveboardViewModel: LiveboardViewModel = viewModel(factory = LiveboardViewModel.Factory),
    station : String?
) {
    val liveboardListState = liveboardViewModel.uiListState.collectAsState()
    val lazyListState = rememberLazyListState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = liveboardViewModel.isRefreshing,
        onRefresh = { liveboardViewModel.getLiveboard(station!!) }
    )

    Surface (
        modifier = modifier.pullRefresh(pullRefreshState),
        color = MaterialTheme.colorScheme.background
    ){
        if (liveboardListState.value.liveboard.departures.departure.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(42.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        modifier = Modifier.size(64.dp)
                    )
                    Text(
                        text = "Komend uur geen vertrekkende treinen gevonden",
                        modifier = Modifier.padding(top = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                state = lazyListState,
                modifier = modifier
                    .fillMaxSize()
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
        Box(
            modifier = Modifier.height(48.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            PullRefreshIndicator(
                state = pullRefreshState,
                refreshing = liveboardViewModel.isRefreshing
            )
        }
    }
}
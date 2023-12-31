package com.example.mad_android.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun StationsScreen(
    stationViewModel: StationViewModel = viewModel(factory = StationViewModel.Factory)
) {
    val stationListState = stationViewModel.uiListState.collectAsState()

    val searchText = stationViewModel.searchText.collectAsState()
    val isSearching = stationViewModel.isSearching.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = searchText.value,
                onValueChange = { stationViewModel.onSeachTextChange(it) },
                label = { Text(text = "Search") },
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
            val lazyListState = rememberLazyListState()
            LazyColumn(state = lazyListState) {
                items(stationListState.value.station.station.size) { index ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        Text(
                            text = stationListState.value.station.station[index].name,
                            modifier = Modifier.padding(16.dp)
                        )

                    }
                }
            }
        }
    }
}
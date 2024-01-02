package com.example.mad_android.ui.screens.schedule

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mad_android.R
import com.example.mad_android.ui.screens.schedule.components.DepartureCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    liveboardViewModel: LiveboardViewModel,
    modifier: Modifier = Modifier,
    navController: NavController,
    station : String?
) {
    val liveboardListState = liveboardViewModel.uiListState.collectAsState()
    val lazyListState = rememberLazyListState()

    Column(
        modifier = modifier.fillMaxSize()
    ){
        TopAppBar(
            modifier = modifier.height(IntrinsicSize.Min),
            title = {
                Text(text = liveboardListState.value.liveboard.stationinfo.standardname)
            },
            navigationIcon = {
                 Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = colorResource(id = R.color.white),
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }
                 )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colorResource(id = R.color.purple_500),
                titleContentColor = colorResource(id = R.color.white),
            )
        )
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
        Log.d("probleem", "ScheduleScreen: $station")
        liveboardViewModel.getLiveboard(station!!)
    }
}
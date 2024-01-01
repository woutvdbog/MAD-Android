package com.example.mad_android.ui.screens.schedule

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.navigation.NavController
import com.example.mad_android.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    liveboardViewModel: LiveboardViewModel,
    modifier: Modifier = Modifier,
    navController: NavController,
    station : String?
) {
    val liveboardListState = liveboardViewModel.uiListState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize()
    ){
        TopAppBar(
            modifier = modifier.height(IntrinsicSize.Min),
            title = { Text(text = "Schedule") },
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
        Text(text = liveboardListState.value.liveboard.station)
        
        Button(onClick = { liveboardViewModel.getLiveboard("Tielt") }) {
            
        }
    }

    LaunchedEffect(true) {
        Log.d("probleem", "ScheduleScreen: $station")
        liveboardViewModel.getLiveboard(station!!)
    }
}
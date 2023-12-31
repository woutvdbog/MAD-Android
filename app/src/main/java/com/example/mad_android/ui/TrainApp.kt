package com.example.mad_android.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mad_android.navigation.StationScreen
import com.example.mad_android.navigation.navComponent

@Composable
fun TrainApp(
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry = navController.currentBackStackEntry

    val currentScreenTitle = try {
        val screen = backStackEntry?.destination?.route?.let { route ->
            StationScreen.values().find { it.name == route } ?: StationScreen.Start
        } ?: StationScreen.Start

        screen.name
    } catch (e: IllegalArgumentException) {
        StationScreen.Start.name
    }

    when {
        currentScreenTitle == StationScreen.Start.name -> {
            navComponent(
                modifier = Modifier,
                navController = navController
            )
        }
    }
}
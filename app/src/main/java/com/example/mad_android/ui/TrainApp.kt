package com.example.mad_android.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mad_android.navigation.StationScreen
import com.example.mad_android.navigation.navComponent
import com.example.mad_android.ui.components.TrainBottomAppBar

@Composable
fun TrainApp(
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry = navController.currentBackStackEntry

    val goToStations = {
        navController.navigate(StationScreen.Start.name) {
            launchSingleTop = true
        }
    }

    val goToFavourites = {
        navController.navigate(StationScreen.Favourites.name) {
            launchSingleTop = true
        }
    }

    val currentScreenTitle = try {
        val screen = backStackEntry?.destination?.route?.let { route ->
            StationScreen.values().find { it.name == route } ?: StationScreen.Start
        } ?: StationScreen.Start

        screen.name
    } catch (e: IllegalArgumentException) {
        StationScreen.Start.name
    }

    Scaffold(
        bottomBar = {
            TrainBottomAppBar(
                goToStations = goToStations,
                goToFavourites = goToFavourites
            )
        }
    ) { innerPadding ->
        navComponent(
            modifier = Modifier.padding(innerPadding),
            navController = navController
        )

    }

//    when {
//        currentScreenTitle == StationScreen.Start.name -> {
//            Scaffold { innerPadding ->
//                navComponent(
//                    modifier = Modifier.padding(innerPadding),
//                    navController = navController
//                )
//            }
//        }
//    }
}
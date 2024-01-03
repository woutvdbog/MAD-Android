package com.example.mad_android.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mad_android.navigation.StationScreen
import com.example.mad_android.navigation.navComponent
import com.example.mad_android.ui.components.TrainBottomAppBar
import com.example.mad_android.ui.components.TrainTopAppBar

@Composable
fun TrainApp(
    navController: NavHostController = rememberNavController(),
) {

    val backStackEntry by navController.currentBackStackEntryAsState()

    val goToStations = {
        navController.navigate(StationScreen.Start.name) {
            launchSingleTop = true

            popUpTo(StationScreen.Start.name) {
                inclusive = true
            }
        }
    }

    val goToFavourites = {
        navController.navigate(StationScreen.Favourites.name) {
            launchSingleTop = true

            popUpTo(StationScreen.Favourites.name) {
                inclusive = true
            }
        }
    }

    val currentScreen = StationScreen.valueOf(
        backStackEntry?.destination?.route?.split("/")?.get(0) ?: StationScreen.Start.name
    )

    Scaffold(
        topBar = {
            TrainTopAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {
                    navController.popBackStack()
                }
            )
        },
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
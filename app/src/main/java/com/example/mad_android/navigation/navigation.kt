package com.example.mad_android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mad_android.ui.screens.favourites.FavouritesScreen
import com.example.mad_android.ui.screens.schedule.LiveboardViewModel
import com.example.mad_android.ui.screens.schedule.ScheduleScreen
import com.example.mad_android.ui.screens.stations.StationsScreen

/**
 * Composable function defining the navigation component for the application.
 *
 * This function sets up the navigation using [NavHost] and defines the composable screens for each destination.
 *
 * @param navController Navigation controller for handling navigation events.
 * @param modifier [Modifier] for styling the navigation component.
 */
@Composable
fun NavComponent(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val liveboardViewModel: LiveboardViewModel = viewModel(factory = LiveboardViewModel.Factory)

    NavHost(
        navController = navController,
        startDestination = Screens.Start.name,
        modifier = modifier
    ) {
        composable(route = Screens.Start.name) { backStackEntry ->
            StationsScreen(
                onStationSelected = { station ->
                    navController.navigate("${Screens.Schedule.name}/$station") {
                        launchSingleTop = true

                        popUpTo(Screens.Schedule.name) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route ="${Screens.Schedule.name}/{station}") { backStackEntry ->
            val station = backStackEntry.arguments?.getString("station")
            ScheduleScreen(
                station = station,
                modifier = Modifier,
                liveboardViewModel = liveboardViewModel)
        }

        composable(route = Screens.Favourites.name) {
            FavouritesScreen(
                onStationSelected = { station ->
                    navController.navigate("${Screens.Schedule.name}/$station") {
                        launchSingleTop = true

                        popUpTo(Screens.Schedule.name) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
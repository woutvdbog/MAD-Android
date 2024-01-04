package com.example.mad_android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mad_android.ui.screens.favourites.FavouriteViewModel
import com.example.mad_android.ui.screens.favourites.FavouritesScreen
import com.example.mad_android.ui.screens.schedule.LiveboardViewModel
import com.example.mad_android.ui.screens.schedule.ScheduleScreen
import com.example.mad_android.ui.screens.stations.StationViewModel
import com.example.mad_android.ui.screens.stations.StationsScreen

@Composable
fun navComponent(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val liveboardViewModel: LiveboardViewModel = viewModel(factory = LiveboardViewModel.Factory)
    val favouriteViewModel: FavouriteViewModel = viewModel(factory = FavouriteViewModel.Factory)
    val stationViewModel: StationViewModel = viewModel(factory = StationViewModel.Factory)

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
                },
                stationViewModel = stationViewModel,
                favouriteViewModel = favouriteViewModel
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
                favouriteViewModel = favouriteViewModel,
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
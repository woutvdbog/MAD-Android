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
import com.example.mad_android.ui.screens.stations.StationsScreen

@Composable
fun navComponent(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val liveboardViewModel: LiveboardViewModel = viewModel(factory = LiveboardViewModel.Factory)
    val favouriteViewModel: FavouriteViewModel = viewModel(factory = FavouriteViewModel.Factory)

    NavHost(
        navController = navController,
        startDestination = StationScreen.Start.name,
        modifier = modifier
    ) {
        composable(StationScreen.Start.name) {backStackEntry ->
            StationsScreen(
                onStationSelected = { station ->
                    navController.navigate("${StationScreen.Schedule.name}/$station")
                },
                favouriteViewModel = favouriteViewModel
            )
        }

        composable("${StationScreen.Schedule.name}/{station}") { backStackEntry ->
            val station = backStackEntry.arguments?.getString("station")
            ScheduleScreen(
                station = station,
                modifier = Modifier,
                navController = navController,
                liveboardViewModel = liveboardViewModel)
        }

        composable(StationScreen.Favourites.name) {
            FavouritesScreen(
                favouriteViewModel = favouriteViewModel,
                onStationSelected = { station ->
                    navController.navigate("${StationScreen.Schedule.name}/$station")
                }
            )
        }
    }
}
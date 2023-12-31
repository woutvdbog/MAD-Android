package com.example.mad_android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mad_android.ui.screens.StationsScreen

@Composable
fun navComponent(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = StationScreen.Start.name,
        modifier = modifier
    ) {
        composable(StationScreen.Start.name) {
            StationsScreen()
        }
    }
}
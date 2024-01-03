package com.example.mad_android.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mad_android.R
import com.example.mad_android.navigation.Screens
import com.example.mad_android.navigation.navComponent
import com.example.mad_android.ui.components.NavigationDrawerContent
import com.example.mad_android.ui.components.TrainAppNavigationRail
import com.example.mad_android.ui.components.TrainBottomAppBar
import com.example.mad_android.ui.components.TrainTopAppBar
import com.example.mad_android.ui.util.TrainAppNavigationType

@Composable
fun TrainApp(
    navigationType: TrainAppNavigationType,
    navController: NavHostController = rememberNavController(),
) {

    val backStackEntry by navController.currentBackStackEntryAsState()

    val goToStations = {
        navController.navigate(Screens.Start.name) {
            launchSingleTop = true

            popUpTo(Screens.Start.name) {
                inclusive = true
            }
        }
    }

    val goToFavourites = {
        navController.navigate(Screens.Favourites.name) {
            launchSingleTop = true

            popUpTo(Screens.Favourites.name) {
                inclusive = false
            }
        }
    }

    val currentScreen = Screens.valueOf(
        backStackEntry?.destination?.route?.split("/")?.get(0) ?: Screens.Start.name
    )

    if(navigationType == TrainAppNavigationType.PERMANENT_NAVIGATION_DRAWER) {
        PermanentNavigationDrawer(drawerContent = {
            PermanentDrawerSheet (
                Modifier.width(dimensionResource(R.dimen.drawer_width))
            ) {
                NavigationDrawerContent(
                    selectedDestination = navController.currentDestination,
                    onTabPressed = { node: String -> navController.navigate(node) },
                    modifier = Modifier
                        .wrapContentWidth()
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.inverseOnSurface)
                        .padding(dimensionResource(R.dimen.drawer_padding_content)),
                )
            }
        }) {

            Scaffold(
                topBar = {
                    TrainTopAppBar(
                        currentScreen = currentScreen,
                        canNavigateBack = navController.previousBackStackEntry != null,
                        navigateUp = {
                            navController.popBackStack()
                        }
                    )
                }
            ) { innerPadding ->
                navComponent(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController
                )
            }
        }
    } else if (navigationType == TrainAppNavigationType.BOTTOM_NAVIGATION) {
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
                    onTabPressed = { node: String -> navController.navigate(node) {
                        launchSingleTop = true

                        popUpTo(node) {
                            inclusive = true
                        }
                    } },
                )
            }
        ) { innerPadding ->
            navComponent(
                modifier = Modifier.padding(innerPadding),
                navController = navController
            )
        }
    } else {
        Row {
            AnimatedVisibility(visible = navigationType == TrainAppNavigationType.NAVIGATION_RAIL) {
                TrainAppNavigationRail(
                    selectedDestination = navController.currentDestination,
                    onTabPressed = { node: String -> navController.navigate(node) },
                )
            }
            Scaffold(
                topBar = {
                    TrainTopAppBar(
                        currentScreen = currentScreen,
                        canNavigateBack = navController.previousBackStackEntry != null,
                        navigateUp = {
                            navController.popBackStack()
                        }
                    )
                }
            ) { innerPadding ->
                navComponent(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController
                )
            }
        }
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
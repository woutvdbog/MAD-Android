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
import com.example.mad_android.navigation.NavComponent
import com.example.mad_android.navigation.Screens
import com.example.mad_android.ui.components.NavigationDrawerContent
import com.example.mad_android.ui.components.TrainAppNavigationRail
import com.example.mad_android.ui.components.TrainBottomAppBar
import com.example.mad_android.ui.components.TrainTopAppBar
import com.example.mad_android.ui.util.TrainAppNavigationType

/**
 * Composable representing the main structure of the TrainApp.
 *
 * @param navigationType The type of navigation used in the app (e.g., BOTTOM_NAVIGATION, NAVIGATION_RAIL).
 * @param navController The NavController used for navigation between screens.
 */
@Composable
fun TrainApp(
    navigationType: TrainAppNavigationType,
    navController: NavHostController = rememberNavController(),
) {

    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = Screens.valueOf(
        backStackEntry?.destination?.route?.split("/")?.get(0) ?: Screens.Start.name
    )

    when (navigationType) {
        TrainAppNavigationType.PERMANENT_NAVIGATION_DRAWER -> {
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
                    NavComponent(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController
                    )
                }
            }
        }
        TrainAppNavigationType.BOTTOM_NAVIGATION -> {
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
                NavComponent(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController
                )
            }
        }
        else -> {
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
                    NavComponent(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController
                    )
                }
            }
        }
    }
}
package com.example.mad_android.screens

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.mad_android.TestUtils.Companion.assertCurrentRouteName
import com.example.mad_android.navigation.Screens
import com.example.mad_android.ui.TrainApp
import com.example.mad_android.ui.util.TrainAppNavigationType
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            TrainApp(navController = navController, navigationType = TrainAppNavigationType.BOTTOM_NAVIGATION)
        }
    }

    @Test
    fun verifyStartDestination() {

        navController.assertCurrentRouteName(Screens.Start.name)

        composeTestRule
            .onAllNodesWithText("Stations")
            .assertCountEquals(2)
    }

    @Test
    fun navigateToFavourites() {
        composeTestRule
            .onNodeWithText("Favorieten")
            .assertIsDisplayed()
            .performClick()
        composeTestRule
            .onAllNodesWithText("Favorieten")
            .assertCountEquals(2)

        navController.assertCurrentRouteName(Screens.Favourites.name)
    }

    @Test
    fun navigateToStations() {
        composeTestRule
            .onNodeWithText("Favorieten")
            .assertIsDisplayed()
            .performClick()
        composeTestRule
            .onNodeWithText("Stations")
            .assertIsDisplayed()
            .performClick()
        composeTestRule
            .onAllNodesWithText("Stations")
            .assertCountEquals(2)

        navController.assertCurrentRouteName(Screens.Start.name)
    }

    @Test
    fun navigateToSchedule() {
        composeTestRule.waitUntil {
            composeTestRule
                .onAllNodesWithTag("StationCard")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onAllNodesWithTag("StationCard")
            .onFirst()
            .performClick()
        composeTestRule
            .onNodeWithTag("StationDetails", useUnmergedTree = true)
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("StationScheduleButton", useUnmergedTree = true)
            .assertIsDisplayed()
            .performClick()

        assertTrue(navController.currentBackStackEntry?.destination?.route?.startsWith(Screens.Schedule.name) == true)
    }

}
package com.example.mad_android.screens

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.mad_android.TestUtils.Companion.assertAreDisplayed
import com.example.mad_android.ui.TrainApp
import com.example.mad_android.ui.util.TrainAppNavigationType
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class StationsScreenTest {
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
    fun verifyStationsLoaded() {
        waitForStations()

        composeTestRule
            .onAllNodesWithTag("StationCard")
            .assertAreDisplayed()
    }

    @Test
    fun viewStationDetails() {
        waitForStations()

        composeTestRule
            .onAllNodesWithTag("StationCard")
            .onFirst()
            .performClick()
        composeTestRule
            .onNodeWithTag("StationDetails", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun viewStationSchedule() {
        waitForStations()

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
        composeTestRule
            .onNodeWithText("Dienstregeling")
            .assertIsDisplayed()
    }

    private fun waitForStations() {
        composeTestRule.waitUntil {
            composeTestRule
                .onAllNodesWithTag("StationCard")
                .fetchSemanticsNodes().isNotEmpty()
        }
    }
}
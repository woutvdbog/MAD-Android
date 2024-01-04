package com.example.mad_android.screens

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.mad_android.ui.TrainApp
import com.example.mad_android.ui.util.TrainAppNavigationType
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
    }

}
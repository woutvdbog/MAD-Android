package com.example.mad_android

import androidx.compose.ui.test.SemanticsNodeInteractionCollection
import androidx.compose.ui.test.assertIsDisplayed
import androidx.navigation.NavController
import org.junit.Assert.assertEquals

class TestUtils {
    companion object {
        fun SemanticsNodeInteractionCollection.assertAreDisplayed(): SemanticsNodeInteractionCollection {
            fetchSemanticsNodes().forEachIndexed { index, _ ->
                get(index).assertIsDisplayed()
            }
            return this
        }

        fun NavController.assertCurrentRouteName(expectedRouteName: String) {
            assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
        }
    }
}
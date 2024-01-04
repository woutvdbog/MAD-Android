package com.example.mad_android

import androidx.compose.ui.test.SemanticsNodeInteractionCollection
import androidx.compose.ui.test.assertIsDisplayed

class TestUtils {
    companion object {
        fun SemanticsNodeInteractionCollection.assertAreDisplayed(): SemanticsNodeInteractionCollection {
            fetchSemanticsNodes().forEachIndexed { index, _ ->
                get(index).assertIsDisplayed()
            }
            return this
        }
    }

}
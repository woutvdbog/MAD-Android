package com.example.mad_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.example.mad_android.ui.TrainApp
import com.example.mad_android.ui.theme.TrainAppTheme
import com.example.mad_android.ui.util.TrainAppNavigationType

/**
 * Main activity class for the TrainApp.
 */
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            TrainAppTheme {
                Surface {
                    val windowSize = calculateWindowSizeClass(activity = this)

                    when (windowSize.widthSizeClass) {
                        WindowWidthSizeClass.Compact -> {
                            TrainApp(TrainAppNavigationType.BOTTOM_NAVIGATION)
                        }

                        WindowWidthSizeClass.Medium -> {
                            TrainApp(TrainAppNavigationType.NAVIGATION_RAIL)
                        }

                        WindowWidthSizeClass.Expanded -> {
                            TrainApp(TrainAppNavigationType.PERMANENT_NAVIGATION_DRAWER)
                        }

                        else -> {
                            TrainApp(TrainAppNavigationType.BOTTOM_NAVIGATION)
                        }
                    }
                }
            }
        }
    }
}


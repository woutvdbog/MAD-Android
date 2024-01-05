package com.example.mad_android.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.example.mad_android.navigation.Screens

/**
 * Composable function for rendering the top app bar.
 *
 * @param currentScreen The current screen being displayed, used for displaying the screen title.
 * @param canNavigateBack Boolean flag indicating whether the screen allows navigation back.
 * @param navigateUp Callback to handle the up navigation action.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainTopAppBar(
    currentScreen: Screens,
    canNavigateBack: Boolean = false,
    navigateUp : () -> Unit = {},
) {
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(text = currentScreen.title)
        },
        navigationIcon = {
            if(canNavigateBack) {
                IconButton(onClick = {
                    navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                    )
                }
            }
        }
    )
}
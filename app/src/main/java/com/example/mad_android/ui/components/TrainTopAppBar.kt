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
import com.example.mad_android.navigation.StationScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainTopAppBar(
    currentScreen: StationScreen,
    canNavigateBack: Boolean = false,
    navigateUp : () -> Unit = {},
) {
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(text = currentScreen.name)
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
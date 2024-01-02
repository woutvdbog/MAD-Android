package com.example.mad_android.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TrainBottomAppBar(
    goToStations: () -> Unit,
    goToFavourites: () -> Unit,
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        actions = {
            IconButton(
                onClick = goToStations,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    Icons.Filled.List,
                    contentDescription = "navigate to the station list"
                )
            }

            IconButton(
                onClick = goToFavourites,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "navigate to favourites"
                )
            }

        }
    )
}
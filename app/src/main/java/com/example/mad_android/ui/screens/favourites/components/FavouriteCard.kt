package com.example.mad_android.ui.screens.favourites.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.mad_android.model.Favourite

/**
 * Composable function for rendering a card representing a favourite station.
 *
 * @param favourite The favourite station to display in the card.
 * @param onFavouriteRemoved Callback to handle the removal of the favourite station.
 * @param onStationSelected Callback to handle the selection of the station associated with the favourite.
 */
@Composable
fun FavouriteCard(
    favourite: Favourite,
    onFavouriteRemoved: (Favourite) -> Unit = {},
    onStationSelected: (String) -> Unit = {},
) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .testTag("FavouriteCard")
            .animateContentSize()
            .clickable {
                onStationSelected(favourite.id)
            }
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = favourite.standardname,
                modifier = Modifier.padding(16.dp)
            )
            IconButton(
                modifier = Modifier.testTag("RemoveFavouriteButton"),
                onClick = {
                    onFavouriteRemoved(favourite)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove from favourites",
                )
            }
        }
    }
}
package com.example.mad_android.ui.screens.favourites.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mad_android.model.Favourite
import com.example.mad_android.ui.screens.favourites.FavouriteViewModel

@Composable
fun FavouriteCard(
    favouriteViewModel: FavouriteViewModel = viewModel(factory = FavouriteViewModel.Factory),
    favourite: Favourite,
    OnFavouriteRemoved: (Favourite) -> Unit = {}
) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .animateContentSize(),
    ) {
        Text(
            text = favourite.standardname,
            modifier = Modifier.padding(16.dp)
        )
        Button(
            onClick = {
                OnFavouriteRemoved(favourite)
            }
        ) {
            Text(text = "Remove from favourites")
        }
    }
}
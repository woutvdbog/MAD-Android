package com.example.mad_android.ui.screens.favourites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mad_android.ui.screens.favourites.components.FavouriteCard

@Composable
fun FavouritesScreen(
    favouriteViewModel: FavouriteViewModel = viewModel(factory = FavouriteViewModel.Factory)
) {
    val favouritesList by favouriteViewModel.favourites.collectAsState()
    val lazyListState = rememberLazyListState()

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
      Column (
            modifier = Modifier
                .fillMaxSize()
        ) {
          LazyColumn(state = lazyListState) {
                items(favouritesList.size) { index ->
                    FavouriteCard(
                        favouriteViewModel = favouriteViewModel,
                        favourite = favouritesList[index],
                        OnFavouriteRemoved = {
                            favouriteViewModel.removeFavourite(it)
                        }
                    )
                }
          }
        }
    }
}
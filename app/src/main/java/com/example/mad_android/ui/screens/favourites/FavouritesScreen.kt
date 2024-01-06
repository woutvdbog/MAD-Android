package com.example.mad_android.ui.screens.favourites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mad_android.R
import com.example.mad_android.ui.screens.favourites.components.FavouriteCard

/**
 * Composable function for displaying the screen that shows the user's favourite stations.
 *
 * @param favouriteViewModel The ViewModel used to manage favourite stations.
 * @param onStationSelected Callback to handle the selection of a station.
 */
@Composable
fun FavouritesScreen(
    favouriteViewModel: FavouriteViewModel = viewModel(factory = FavouriteViewModel.Factory),
    onStationSelected : (String) -> Unit = {},
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
          if(favouritesList.isEmpty()) {
              Box(
                  modifier = Modifier
                      .fillMaxSize()
                      .padding(42.dp),
                  contentAlignment = Alignment.Center
              ) {
                  Column(
                      horizontalAlignment = Alignment.CenterHorizontally,
                  ) {
                      Icon(
                          imageVector = Icons.Default.Favorite,
                          contentDescription = "Favourite Icon",
                          modifier = Modifier.size(64.dp)
                      )
                      Text(
                          text = stringResource(R.string.no_favourites),
                          modifier = Modifier.padding(top = 8.dp),
                          textAlign = TextAlign.Center
                      )
                  }
              }
          } else {
              LazyColumn(state = lazyListState) {
                  items(favouritesList.size) { index ->
                      FavouriteCard(
                          favourite = favouritesList[index],
                          onStationSelected = onStationSelected,
                          onFavouriteRemoved = {
                              favouriteViewModel.removeFavourite(it)
                          }
                      )
                  }
              }
          }
        }
    }
}
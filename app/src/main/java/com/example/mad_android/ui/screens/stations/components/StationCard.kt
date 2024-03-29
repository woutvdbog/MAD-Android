package com.example.mad_android.ui.screens.stations.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mad_android.R
import com.example.mad_android.model.StationObject
import com.example.mad_android.ui.screens.favourites.FavouriteViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

/**
 * A Composable representing a card view for displaying information about a station.
 *
 * This Composable displays a station's name and provides additional details, such as a Google Map
 * showing the station's location when expanded. Users can also view the station's schedule and
 * mark it as a favorite.
 *
 * @param favouriteViewModel The [FavouriteViewModel] used for managing favorite stations.
 * @param station The [StationObject] representing the station to be displayed.
 * @param onStationSelected A lambda function to be called when the station is selected.
 */
@Composable
fun StationCard(
    favouriteViewModel: FavouriteViewModel = viewModel(factory = FavouriteViewModel.Factory),
    station: StationObject,
    onStationSelected: (String) -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }
    val favouritesList by favouriteViewModel.favourites.collectAsState()

    val favourite = favouritesList.find { it.name == station.name }
    val isFavourite = favourite != null

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { isExpanded = !isExpanded }
            .testTag("StationCard")
            .animateContentSize(),
    ) {
        Text(
            text = station.standardname,
            modifier = Modifier.padding(16.dp)
        )
        if(isExpanded) {
            Column {
                val location = LatLng(
                    station.locationY.toDouble(),
                    station.locationX.toDouble()
                )
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(location, 12f)
                }

                GoogleMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    cameraPositionState = cameraPositionState
                )
            }
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .testTag("StationDetails")
                ){
                    Button(
                        modifier = Modifier
                            .weight(0.8f)
                            .padding(4.dp),
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary,
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        onClick = {
                            onStationSelected(station.id)
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.view_schedule),
                            modifier = Modifier.testTag("StationScheduleButton")
                        )
                    }
                    Button(
                        modifier = Modifier
                            .weight(0.2f)
                            .testTag("StationFavouriteButton")
                            .padding(4.dp),
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.primaryContainer,
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        onClick = {
                            if(isFavourite) {
                                favouriteViewModel.removeFavourite(favourite!!)
                            } else {
                                favouriteViewModel.addFavourite(station)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if(isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite"
                        )
                    }
                }
            }
        }
    }
}
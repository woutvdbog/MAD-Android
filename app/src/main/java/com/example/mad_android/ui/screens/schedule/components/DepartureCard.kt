package com.example.mad_android.ui.screens.schedule.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.mad_android.model.Departure
import com.example.mad_android.model.Platform
import com.example.mad_android.model.Vehicle

/**
 * Composable function to display a departure card with information about a train departure.
 * @param modifier Modifier for customizing the appearance of the DepartureCard.
 * @param departure Departure object containing information about the train departure.
 */
@Composable
fun DepartureCard(
    modifier: Modifier = Modifier,
    departure: Departure
) {
    Card (
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){

            Column (
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxHeight()
            ) {
                if(departure.canceled == 1) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    textDecoration = TextDecoration.LineThrough,
                                    color = MaterialTheme.colorScheme.error,
                                )
                            ) {
                                append(departure.stationinfo.standardname)
                            }
                        }
                    )
                } else {
                    Text(
                        text = departure.stationinfo.standardname,
                    )
                }
                Row {
                    Text(
                        text = departure.time,
                    )
                    if(departure.delay != 0) {
                        val delayInMinutes = departure.delay / 60
                        Text(
                            text = " + ${delayInMinutes}'",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
            Row (
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ){
                TrainType(departure.vehicleinfo)
                PlatformBox(departure.platforminfo)
            }
        }
    }
}

/**
 * Composable function to display the train type in a rounded box.
 * @param vehicle Vehicle object containing information about the train type.
 */
@Composable
fun TrainType(vehicle: Vehicle) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .widthIn(min = 32.dp),
        shape = RoundedCornerShape(8.dp),
        color = contentColorFor(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = vehicle.type,
            modifier = Modifier
                .padding(8.dp),
            textAlign = TextAlign.Center,
        )
    }
}

/**
 * Composable function to display the platform information in a rounded box.
 * @param platform Platform object containing information about the platform.
 */
@Composable
fun PlatformBox(platform: Platform) {
    val platformColor = if(platform.normal == "0") MaterialTheme.colorScheme.error else contentColorFor(MaterialTheme.colorScheme.primary)
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .widthIn(min = 32.dp),
        shape = RoundedCornerShape(8.dp),
        color = platformColor
    ) {
        Text(
            text = platform.name,
            modifier = Modifier
                .padding(8.dp),
            textAlign = TextAlign.Center,
        )
    }
}
package com.example.mad_android.ui.screens.schedule.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mad_android.model.Departure
import com.example.mad_android.model.Platform

@Composable
fun DepartureCard(
    modifier: Modifier = Modifier,
    departure: Departure
) {
    Card (
        modifier = modifier
            .fillMaxWidth()

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
                Text(
                    text = departure.stationinfo.standardname
                )
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

            PlatformBox(departure.platforminfo)
        }
    }
    Spacer(modifier = Modifier.padding(4.dp))
}

@Composable
fun PlatformBox(platform: Platform) {
    val platformColor = if(platform.normal == "0") MaterialTheme.colorScheme.error else contentColorFor(MaterialTheme.colorScheme.primary)
    Surface(
        modifier = Modifier
            .padding(8.dp)
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
package com.example.mad_android.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Composable function for displaying an error message with a warning icon.
 */
@Composable
fun Error() {
    Column (
        modifier = Modifier.fillMaxSize()
            .padding(62.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.Filled.Warning,
            contentDescription = "Error",
            modifier = Modifier.size(82.dp)
        )
        Text(text = "Er ging iets fout, controleer je internetverbinding",
            modifier = Modifier.padding(top = 16.dp),
            textAlign = TextAlign.Center)
    }
}
package com.example.mad_android.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import com.example.mad_android.navigation.NavItems

/**
 * Composable function for rendering the bottom app bar.
 *
 * @param onTabPressed Callback for handling tab selection in the bottom app bar.
 */
@Composable
fun TrainBottomAppBar(
    onTabPressed: ((String) -> Unit),
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        actions = {
            for(navItem in NavItems.values()) {
                IconButton(
                    onClick = {
                        onTabPressed(navItem.name)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Column {
                        Icon(
                            imageVector = navItem.icon,
                            modifier = Modifier.align(CenterHorizontally),
                            contentDescription = navItem.title
                        )
                        Text(
                            text = navItem.title
                        )
                    }
                }
            }

        }
    )
}
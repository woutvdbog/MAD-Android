package com.example.mad_android.ui.components

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mad_android.navigation.NavItems

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
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.title
                    )
                }
            }

        }
    )
}
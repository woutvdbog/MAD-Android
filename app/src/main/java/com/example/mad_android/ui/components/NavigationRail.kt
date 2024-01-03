package com.example.mad_android.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import com.example.mad_android.navigation.NavItems

@Composable
fun TrainAppNavigationRail(
    selectedDestination: NavDestination?,
    onTabPressed: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationRail(
        modifier = modifier
    ) {
        for(navItem in NavItems.values()) {
            NavigationRailItem(
                selected = selectedDestination?.route == navItem.name,
                onClick = {
                    onTabPressed(navItem.name)
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.title
                    )
                }
            )
        }
    }
}
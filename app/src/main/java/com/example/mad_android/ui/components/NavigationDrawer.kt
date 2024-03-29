package com.example.mad_android.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavDestination
import com.example.mad_android.R
import com.example.mad_android.navigation.NavItems

/**
 * Composable function for rendering the content of the navigation drawer.
 *
 * @param selectedDestination The currently selected destination in the navigation.
 * @param onTabPressed Callback for handling tab selection in the navigation drawer.
 * @param modifier Modifier for styling and layout adjustments.
 */
@Composable
fun NavigationDrawerContent(
    selectedDestination: NavDestination?,
    onTabPressed: ((String) -> Unit),
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        for (navItem in NavItems.values()) {
            NavigationDrawerItem(
                selected = selectedDestination?.route == navItem.name,
                label = {
                    Text(
                        text = navItem.title,
                        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.drawer_padding_header)),
                    )
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.title
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color.Transparent,
                ),
                onClick = { onTabPressed(navItem.name) },
            )
        }
    }
}
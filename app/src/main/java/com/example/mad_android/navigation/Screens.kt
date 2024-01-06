package com.example.mad_android.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Enum class representing different screens in the application with their associated titles.
 *
 * Each enum constant corresponds to a specific screen in the app, and the [title] property provides the
 * title to be displayed in the app.
 */
enum class Screens(val title: String) {
    Start("Stations"),
    Schedule("Dienstregeling"),
    Favourites("Favorieten");
}

/**
 * Enum class representing navigation items with their associated titles and icons.
 *
 * Each enum constant corresponds to a specific navigation item, and the [title] property provides the
 * title to be displayed within the app, while the [icon] property holds the associated [ImageVector] icon.
 */
enum class NavItems(val title: String, val icon: ImageVector) {
    Start("Stations", icon = Icons.Filled.LocationOn),
    Favourites("Favorieten", icon = Icons.Filled.Favorite);
}
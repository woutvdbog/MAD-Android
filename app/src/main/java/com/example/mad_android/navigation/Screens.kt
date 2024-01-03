package com.example.mad_android.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector

enum class Screens(val title: String) {
    Start("Stations"),
    Schedule("Dienstregeling"),
    Favourites("Favorieten");
}

enum class NavItems(val title: String, val icon: ImageVector) {
    Start("Stations", icon = Icons.Filled.LocationOn),
    Favourites("Favorieten", icon = Icons.Filled.Favorite);
}
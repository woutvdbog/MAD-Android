package com.example.mad_android.navigation

enum class StationScreen(val route: String) {
    Start("start"),
    Schedule("schedule"),
    Favourites("favourites");

    companion object {
        fun fromRoute(route: String): StationScreen {
            return values().firstOrNull { it.route == route } ?: Start
        }
    }
}
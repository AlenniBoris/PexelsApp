package com.example.pexapp.presentation.navigation

import com.example.pexapp.R

sealed class AppScreen(
    val route: String,
    val activeIcon: Int? = null,
    val notActiveIcon: Int? = null
) {
    data object Home : AppScreen(
        route = "homescreen",
        activeIcon = R.drawable.icon_home_active,
        notActiveIcon = R.drawable.icon_home_not_active
    )

    data object Favourite : AppScreen(
        route = "favouritescreen",
        activeIcon = R.drawable.icon_favourites_active,
        notActiveIcon = R.drawable.icon_favourites_not_active
    )

    data object Details : AppScreen("detailsscreen/")

    data object Settings : AppScreen(
        route = "settingsscreen",
        activeIcon = R.drawable.settings_icon_active,
        notActiveIcon = R.drawable.settings_icon
    )
}
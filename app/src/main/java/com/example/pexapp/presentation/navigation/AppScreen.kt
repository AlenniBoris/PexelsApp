package com.example.pexapp.presentation.navigation

import com.example.pexapp.R

sealed class AppScreen(
    val route: String,
    val activeIcon: Int? = null,
    val notActiveIcon: Int? = null
) {
    data object Home : AppScreen(
        "homescreen",
        R.drawable.icon_home_active,
        R.drawable.icon_home_not_active
    )

    data object Favourite : AppScreen(
        "favouritescreen",
        R.drawable.icon_favourites_active,
        R.drawable.icon_favourites_not_active
    )

    data object Details : AppScreen("detailsscreen/")
}
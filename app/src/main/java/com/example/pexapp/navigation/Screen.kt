package com.example.pexapp.navigation

import com.example.pexapp.R

sealed class Screen(
    val title: String,
    val route: String,
    val activeIcon: Int? = null,
    val notActiveIcon: Int? = null
) {
    object Main: Screen(
        "Home",
        "mainscreen",
        R.drawable.icon_home_active,
        R.drawable.icon_home_not_active
    )

    object Favourite: Screen(
        "Favourite",
        "favouritescreen",
        R.drawable.icon_favourites_active,
        R.drawable.icon_favourites_not_active
    )

    object Details: Screen("Details", "detailsscreen/")

    object Splash: Screen("Splash", "splashscreen")
}

val screensToNavigate = listOf(
    Screen.Main,
    Screen.Favourite,
    Screen.Details,
    Screen.Splash
)
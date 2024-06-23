package com.example.pexapp.navigation

import com.example.pexapp.R

sealed class Screen(
    val route: String,
    val activeIcon: Int? = null,
    val notActiveIcon: Int? = null
) {
    object Main: Screen(
        "mainscreen",
        R.drawable.icon_home_active,
        R.drawable.icon_home_not_active
    )

    object Favourite: Screen(
        "favouritescreen",
        R.drawable.icon_favourites_active,
        R.drawable.icon_favourites_not_active
    )

    object Details: Screen("detailsscreen/")

}
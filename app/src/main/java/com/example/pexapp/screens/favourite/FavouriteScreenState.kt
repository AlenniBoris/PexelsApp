package com.example.pexapp.screens.favourite

import com.example.pexapp.data.model.Photo

data class FavouriteScreenState(
    val favouritePhotos: List<Photo> = emptyList(),
    val isNoFavourite: Boolean = false
)
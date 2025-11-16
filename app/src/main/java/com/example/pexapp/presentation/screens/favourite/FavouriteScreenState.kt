package com.example.pexapp.presentation.screens.favourite

import com.example.pexapp.domain.model.PhotoModelDomain

data class FavouriteScreenState(
    val favouritePhotos: List<PhotoModelDomain> = emptyList(),
    val isNoFavourite: Boolean = false
)
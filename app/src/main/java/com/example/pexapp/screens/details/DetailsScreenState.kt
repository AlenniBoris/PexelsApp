package com.example.pexapp.screens.details

import com.example.pexapp.data.model.Photo

data class DetailsScreenState(
    val currentPhoto: Photo? = null,
    val photoIsFavourite: Boolean = false
)
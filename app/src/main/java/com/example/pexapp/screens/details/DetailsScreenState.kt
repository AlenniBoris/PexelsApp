package com.example.pexapp.screens.details

import com.example.pexapp.domain.model.PhotoModelDomain

data class DetailsScreenState(
    val currentPhoto: PhotoModelDomain? = null,
    val photoIsFavourite: Boolean = false
)
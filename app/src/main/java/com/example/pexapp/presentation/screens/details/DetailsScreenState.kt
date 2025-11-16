package com.example.pexapp.presentation.screens.details

import com.example.pexapp.domain.model.PhotoModelDomain

data class DetailsScreenState(
    val currentPhoto: PhotoModelDomain? = null,
    val photoIsFavourite: Boolean = false
)
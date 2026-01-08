package com.example.pexapp.presentation.screens.favourite

import com.example.pexapp.presentation.model.PhotoModelUi

data class FavouriteScreenState(
    private val photos: List<PhotoModelUi> = emptyList()
){
    val workingList = photos.reversed()
}
package com.example.pexapp.presentation.screens.favourite

import com.example.pexapp.presentation.model.PhotoModelUi

interface IFavouriteScreenEvent {
    data class OpenPhoto(val photo: PhotoModelUi) : IFavouriteScreenEvent
    data object OpenHomeScreen : IFavouriteScreenEvent
}
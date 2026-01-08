package com.example.pexapp.presentation.screens.favourite

import com.example.pexapp.presentation.model.PhotoModelUi

sealed interface IFavouriteScreenIntent {
    data class OpenPhoto(val photo: PhotoModelUi) : IFavouriteScreenIntent
    data object OpenHomeScreen : IFavouriteScreenIntent
}
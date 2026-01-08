package com.example.pexapp.presentation.screens.home

import com.example.pexapp.presentation.model.PhotoModelUi

sealed interface IHomeScreenEvent {
    data class ShowMessage(val messageId: Int) : IHomeScreenEvent
    data class OpenPhoto(val photo: PhotoModelUi) : IHomeScreenEvent
}
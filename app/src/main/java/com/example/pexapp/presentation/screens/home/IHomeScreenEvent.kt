package com.example.pexapp.presentation.screens.home

sealed interface IHomeScreenEvent {
    data class ShowMessage(val messageId: Int) : IHomeScreenEvent
    data class OpenPicture(val pictureId: String) : IHomeScreenEvent
}
package com.example.pexapp.presentation.screens.details

import com.example.pexapp.presentation.model.PhotoModelUi

interface IDetailsScreenEvent {
    data object NavigateBack : IDetailsScreenEvent
    data class ShowToast(val messageId: Int): IDetailsScreenEvent
    data class OpenPhoto(val photo: PhotoModelUi?): IDetailsScreenEvent
    data object OpenSettings: IDetailsScreenEvent
}
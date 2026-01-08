package com.example.pexapp.presentation.screens.details

import android.content.ContentResolver
import com.example.pexapp.presentation.model.PhotoModelUi
import com.example.pexapp.presentation.uikit.utils.PermissionType

sealed interface IDetailsScreenIntent {
    data object NavigateBack : IDetailsScreenIntent
    data object ProceedLikedAction : IDetailsScreenIntent
    data class StartDownloading(val resolver: ContentResolver?) : IDetailsScreenIntent
    data object DownloadPhoto : IDetailsScreenIntent
    data object RetryPhotoLoad : IDetailsScreenIntent
    data object RefreshData : IDetailsScreenIntent
    data object LoadMoreThemedPhotos : IDetailsScreenIntent
    data class OpenPhotoDetails(val photo: PhotoModelUi) : IDetailsScreenIntent
    data class UpdateRequestedPermissionAndShowDialog(val newRequestedPermission: PermissionType) :
        IDetailsScreenIntent

    data object HidePermissionDialog : IDetailsScreenIntent
    data object OpenSettingsAndHidePermissionDialog : IDetailsScreenIntent
}
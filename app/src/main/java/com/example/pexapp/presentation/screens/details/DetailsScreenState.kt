package com.example.pexapp.presentation.screens.details

import com.example.pexapp.domain.model.CommonExceptionModelDomain
import com.example.pexapp.presentation.model.ExceptionModelUi
import com.example.pexapp.presentation.model.PhotoModelUi
import com.example.pexapp.presentation.model.toUiModel
import com.example.pexapp.presentation.uikit.utils.PermissionType

sealed class DetailsScreenState(
    open val photo: PhotoModelUi? = null,
    open val isLiked: Boolean = false,
    open val isRefreshing: Boolean = false
) {

    data object Initial : DetailsScreenState()

    data class DetailsWithInternet(
        override val photo: PhotoModelUi,
        override val isLiked: Boolean = false,
        override val isRefreshing: Boolean = false,
        val isLoadingMorePossible: Boolean = true,
        val isThemedPhotosLoading: Boolean = false,
        val themedPhotos: List<PhotoModelUi> = emptyList(),
        val currentPage: Int = DetailsScreenValues.START_PAGE,
        val pageSize: Int = DetailsScreenValues.PAGE_SIZE,
        val isLoadingToMemory: Boolean = false,
        val requestedPermission: PermissionType? = null,
        val isPermissionDialogVisible: Boolean = false
    ) : DetailsScreenState(
        photo = photo,
        isLiked = isLiked,
        isRefreshing = isRefreshing
    )

    data class DetailsWithoutInternet(
        override val photo: PhotoModelUi,
        override val isLiked: Boolean = false,
        override val isRefreshing: Boolean = false
    ) : DetailsScreenState(
        photo = photo,
        isLiked = isLiked,
        isRefreshing = isRefreshing
    )

    data class Error(
        val exception: ExceptionModelUi = CommonExceptionModelDomain.UnknownException.toUiModel()
    ) : DetailsScreenState()
}
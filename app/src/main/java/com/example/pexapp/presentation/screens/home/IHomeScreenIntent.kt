package com.example.pexapp.presentation.screens.home

import com.example.pexapp.presentation.model.FeaturedCollectionModelUi
import com.example.pexapp.presentation.model.PhotoModelUi

sealed interface IHomeScreenIntent {
    data object RetryPhotosRequest : IHomeScreenIntent
    data object RefreshData : IHomeScreenIntent
    data class OpenPhotoDetails(val photo: PhotoModelUi) : IHomeScreenIntent
    data object LoadMoreData : IHomeScreenIntent
    data class SelectFeaturedCollection(val collection: FeaturedCollectionModelUi) :
        IHomeScreenIntent

    data object RetryFeaturedCollectionsRequest : IHomeScreenIntent

    data class ChangeQuery(val newValue: String) : IHomeScreenIntent
    data object ClearQuery : IHomeScreenIntent
    data class ChangeHistoryVisibility(val isVisible: Boolean) : IHomeScreenIntent
    data object ForceSearchPhotos : IHomeScreenIntent
    data class DeleteHistoryQuery(val query: String) : IHomeScreenIntent
}
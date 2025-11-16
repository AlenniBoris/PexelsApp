package com.example.pexapp.presentation.screens.home

import com.example.pexapp.presentation.model.ExceptionModelUi
import com.example.pexapp.presentation.model.FeaturedCollectionModelUi
import com.example.pexapp.presentation.model.PhotoModelUi

data class HomeScreenState(
    val isPhotosLoading: Boolean = false,
    val photos: List<PhotoModelUi> = emptyList(),
    val exception: ExceptionModelUi? = null,
    val isRefreshing: Boolean = false,
    val currentPage: Int = HomeScreenValues.START_PAGE,
    val pageSize: Int = HomeScreenValues.PAGE_SIZE,
    val isLoadingMorePossible: Boolean = true,
    val isFeaturedLoading: Boolean = false,
    val featuredCollections: List<FeaturedCollectionModelUi> = emptyList(),
    val selectedFeaturedCollection: FeaturedCollectionModelUi? = null,
    val initialFeaturedCollections: List<FeaturedCollectionModelUi> = emptyList(),
    val query: String = "",
    val history: Set<String> = emptySet(),
    val isHistoryVisible: Boolean = false,
    val suppressDebounce: Boolean = false,
) {
    val workingQuery: String = query.trim().replace(Regex("\\s+"), " ")
    val nextPage: Int = currentPage + 1
    val workingHistory: List<String> = history.toList().reversed()
}
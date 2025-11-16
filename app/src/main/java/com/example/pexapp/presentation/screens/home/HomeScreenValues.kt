package com.example.pexapp.presentation.screens.home

import com.example.pexapp.domain.model.CollectionsModelDomain
import com.example.pexapp.presentation.model.FeaturedCollectionModelUi

object HomeScreenValues {
    const val START_PAGE = 1
    const val PAGE_SIZE = 0
    const val LOADING_BOUNDARY_LIMIT = 1
    val DEFAULT_FEATURED_LIST = List(7) {
        FeaturedCollectionModelUi(
            domainModel = CollectionsModelDomain(
                id = it.toString(),
                title = "",
                description = "",
                private = false,
                mediaCount = it,
                photosCount = it,
                videosCount = it
            )
        )
    }

    const val USER_FRIENDLY_TIME = 2500L
}
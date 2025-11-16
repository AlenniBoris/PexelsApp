package com.example.pexapp.screens.main

import androidx.compose.runtime.Immutable
import com.example.pexapp.domain.model.CollectionsModelDomain
import com.example.pexapp.domain.model.PhotoModelDomain

@Immutable
data class MainScreenState(
    val photos: List<PhotoModelDomain> = emptyList(),
    val errorState: Boolean = false,
    val isActive: Boolean = false,
    val history: Set<String> = emptySet(),
    val queryText: String = "",
    val featuredCollections: List<CollectionsModelDomain> = emptyList(),
    val initialFeaturedCollections: List<CollectionsModelDomain> = emptyList(),
    val selectedFeaturedCollectionId: String = "",
)

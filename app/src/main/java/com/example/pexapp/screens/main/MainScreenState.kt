package com.example.pexapp.screens.main

import androidx.compose.runtime.Immutable
import com.example.pexapp.data.model.Collections
import com.example.pexapp.data.model.Photo

@Immutable
data class MainScreenState(
    val photos: List<Photo> = emptyList(),
    val errorState: Boolean = false,
    val isActive: Boolean = false,
    val history: Set<String> = emptySet(),
    val queryText: String = "",
    val featuredCollections: List<Collections> = emptyList(),
    val initialFeaturedCollections: List<Collections> = emptyList(),
    val selectedFeaturedCollectionId: String = "",
)

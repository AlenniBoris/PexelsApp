package com.example.pexapp.domain.model

data class CollectionsModelDomain(
    val id: String,
    val title: String,
    val description: String,
    val private: Boolean,
    val mediaCount: Int,
    val photosCount: Int,
    val videosCount: Int
)

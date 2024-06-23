package com.example.pexapp.data.model

data class Collections(
    val id: String,
    val title: String,
    val description: String?,
    val private: Boolean,
    val mediaCount: Int,
    val photosCount: Int,
    val videosCount: Int
)

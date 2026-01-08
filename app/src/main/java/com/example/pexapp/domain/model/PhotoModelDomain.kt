package com.example.pexapp.domain.model

data class PhotoModelDomain(
    val id: Long,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val photographerUrl: String,
    val photographerId: Long,
    val avgColor: String,
    val src: PhotoFeaturesModelDomain,
    val liked: Boolean,
    val alt: String
)
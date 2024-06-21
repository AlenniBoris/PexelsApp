package com.example.pexapp.entity

import java.io.Serializable

data class FeaturedCollectionsResponseEntity(
    val collections: List<CollectionsEntity>,
    val page: Int,
    val per_page: Int,
    val total_results: Int,
    val next_page: String,
    val prev_page: String
)

data class CollectionsEntity(
    val id: String,
    val title: String,
    val description: String?,
    val private: Boolean,
    val media_count: Int,
    val photos_count: Int,
    val videos_count: Int
)

data class PhotosResponseEntity(
    val page: Int,
    val perPage: Int,
    val photos: List<PhotoEntity>,
    val totalResults: Int,
    val nextPage: String?
)

data class PhotoEntity(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String?,
    val photographerUrl: String?,
    val photographerId: Int?,
    val avgColor: String?,
    val src: PhotoSrcEntity,
    val liked: Boolean,
    val alt: String

) : Serializable

data class PhotoSrcEntity(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)
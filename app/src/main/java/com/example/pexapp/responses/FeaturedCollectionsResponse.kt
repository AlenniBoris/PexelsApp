package com.example.pexapp.responses

import com.example.pexapp.entity.CollectionsEntity

data class FeaturedCollectionsResponse(
    val collections: List<Collections>,
    val page: Int,
    val per_page: Int,
    val total_results: Int,
    val prev_page: Int,
    val next_page: Int
)

data class Collections(
    val id: String,
    val title: String,
    val description: String?,
    val private: Boolean,
    val media_count: Int,
    val photos_count: Int,
    val videos_count: Int
)

fun List<Collections>.asEntity(): List<CollectionsEntity> {
    return map {
        CollectionsEntity (
            id = it.id,
            title = it.title,
            description = it.description,
            private = it.private,
            media_count = it.media_count,
            photos_count = it.photos_count,
            videos_count = it.videos_count
        )
    }
}
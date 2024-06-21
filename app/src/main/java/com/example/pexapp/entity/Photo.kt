package com.example.pexapp.entity

import java.io.Serializable

data class Photo(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val photographer_url: String,
    val photographer_id: Int,
    val avg_color: String,
    val src: PhotoFeatures,
    val liked: Boolean,
    val alt: String
) : Serializable

data class PhotoFeatures(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)

fun Photo.asEntity(): PhotoEntity {
    return PhotoEntity(
        id = this.id,
        width = this.width,
        height = this.height,
        url = this.url,
        photographer = this.photographer,
        photographerUrl = this.photographer_url,
        photographerId = this.photographer_id,
        avgColor = this.avg_color,
        src = PhotoSrcEntity(
            original = this.src.original,
            large2x = this.src.large2x,
            large = this.src.large,
            medium = this.src.medium,
            small = this.src.small,
            portrait = this.src.portrait,
            landscape = this.src.landscape,
            tiny = this.src.tiny,
        ),
        liked = this.liked,
        alt = this.alt
    )
}
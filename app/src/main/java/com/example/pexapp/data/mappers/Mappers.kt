package com.example.pexapp.data.mappers

import com.example.pexapp.data.source.dao.model.PhotoEntity
import com.example.pexapp.data.source.api.model.CollectionsResponse
import com.example.pexapp.data.source.api.model.PhotoResponse
import com.example.pexapp.data.model.Collections
import com.example.pexapp.data.model.Photo
import com.example.pexapp.data.model.PhotoFeatures
import com.example.pexapp.data.source.dao.model.PhotoFeaturesEntity

fun PhotoEntity.asPhoto() : Photo {
    return Photo(
        id = this.id,
        width = this.width,
        height = this.height,
        url = this.url,
        photographer = this.photographer,
        photographerUrl = this.photographerUrl,
        photographerId = this.photographerId,
        avgColor = this.avgColor,
        src = PhotoFeatures(
            original = this.src.original,
            large2x = this.src.large2x,
            large = this.src.large,
            medium = this.src.medium,
            small = this.src.small,
            portrait = this.src.portrait,
            landscape = this.src.landscape,
            tiny = this.src.tiny
        ),
        liked = this.liked,
        alt = this.alt
    )
}

fun CollectionsResponse.asCollections(): Collections {
    return Collections(
        id = this.id,
        title = this.title,
        description = this.description,
        private = this.private,
        mediaCount = this.mediaCount,
        photosCount = this.photosCount,
        videosCount = this.videosCount
    )
}

fun PhotoResponse.asPhoto() : Photo {
    return Photo(
        id = this.id,
        width = this.width,
        height = this.height,
        url = this.url,
        photographer = this.photographer,
        photographerUrl = this.photographerUrl,
        photographerId = this.photographerId,
        avgColor = this.avgColor,
        src = PhotoFeatures(
            original = this.src.original,
            large2x = this.src.large2x,
            large = this.src.large,
            medium = this.src.medium,
            small = this.src.small,
            portrait = this.src.portrait,
            landscape = this.src.landscape,
            tiny = this.src.tiny
        ),
        liked = this.liked,
        alt = this.alt
    )
}

fun Photo.asPhotoEntity(): PhotoEntity {
    return PhotoEntity(
        id = id,
        width = width,
        height = height,
        url = url,
        photographer = photographer,
        photographerUrl = photographerUrl,
        photographerId = photographerId,
        avgColor = avgColor,
        src = PhotoFeaturesEntity(
            original = src.original,
            large2x = src.large2x,
            large = src.large,
            medium = src.medium,
            small = src.small,
            portrait = src.portrait,
            landscape = src.landscape,
            tiny = src.tiny
        ),
        liked = liked,
        alt = alt
    )
}

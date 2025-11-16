package com.example.pexapp.data.mappers

import android.util.Log
import com.example.pexapp.data.source.api.model.CollectionsResponseModelData
import com.example.pexapp.data.source.api.model.PhotoResponseModelData
import com.example.pexapp.data.source.dao.model.PhotoEntityModelData
import com.example.pexapp.domain.model.CollectionsModelDomain
import com.example.pexapp.domain.model.PhotoFeaturesModelDomain
import com.example.pexapp.domain.model.PhotoModelDomain
import com.example.pexapp.domain.model.PhotoSimpleModelDomain

fun PhotoEntityModelData.asPhotoSimpleDomainModel(): PhotoSimpleModelDomain =
    PhotoSimpleModelDomain(
        id = this.id,
        photographer = this.photographer,
        photoPictureUrl = this.photoPictureUrl
    )

fun PhotoSimpleModelDomain.asEntityModelData(): PhotoEntityModelData =
    PhotoEntityModelData(
        id = this.id,
        photographer = this.photographer,
        photoPictureUrl = this.photoPictureUrl
    )

fun CollectionsResponseModelData.asCollectionsModelDomain(): CollectionsModelDomain? = runCatching {
    CollectionsModelDomain(
        id = this.id!!,
        title = this.title!!,
        description = this.description!!,
        private = this.private.toBoolean(),
        mediaCount = this.mediaCount?.toInt()!!,
        photosCount = this.photosCount?.toInt()!!,
        videosCount = this.videosCount?.toInt()!!
    )
}.getOrElse {
    Log.e("!!!!", it.stackTraceToString())
    null
}

fun PhotoResponseModelData.asPhotoDomainModel(): PhotoModelDomain? = runCatching {
    PhotoModelDomain(
        id = this.id?.toLong()!!,
        width = this.width?.toInt()!!,
        height = this.height?.toInt()!!,
        url = this.url!!,
        photographer = this.photographer!!,
        photographerUrl = this.photographerUrl!!,
        photographerId = this.photographerId?.toLong()!!,
        avgColor = this.avgColor!!,
        src = PhotoFeaturesModelDomain(
            original = this.src!!.original!!,
            large2x = this.src.large2x!!,
            large = this.src.large!!,
            medium = this.src.medium!!,
            small = this.src.small!!,
            portrait = this.src.portrait!!,
            landscape = this.src.landscape!!,
            tiny = this.src.tiny!!
        ),
        liked = this.liked.toBoolean(),
        alt = this.alt!!
    )
}.getOrElse {
    Log.e("!!!!", it.stackTraceToString())
    null
}

fun PhotoModelDomain.asPhotoEntityModelData(): PhotoEntityModelData {
    return PhotoEntityModelData(
        id = this.id,
        photographer = this.photographer,
        photoPictureUrl = this.src.medium
    )
}

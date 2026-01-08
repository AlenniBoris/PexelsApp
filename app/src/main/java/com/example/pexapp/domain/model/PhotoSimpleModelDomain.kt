package com.example.pexapp.domain.model

data class PhotoSimpleModelDomain(
    val id: Long,
    val photoPictureMediumSizeUrl: String,
    val photoPictureOriginalSizeUrl: String,
    val photographer: String
)

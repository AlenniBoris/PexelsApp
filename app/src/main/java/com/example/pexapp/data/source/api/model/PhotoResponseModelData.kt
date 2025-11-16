package com.example.pexapp.data.source.api.model

import com.google.gson.annotations.SerializedName

data class PhotoResponseModelData(
    val id: String?,
    val width: String?,
    val height: String?,
    val url: String?,
    val photographer: String?,
    @SerializedName("photographer_url")
    val photographerUrl: String?,
    @SerializedName("photographer_id")
    val photographerId: String?,
    @SerializedName("avg_color")
    val avgColor: String?,
    val src: PhotoFeaturesResponseModelData?,
    val liked: String?,
    val alt: String?
)

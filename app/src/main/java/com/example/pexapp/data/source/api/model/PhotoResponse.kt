package com.example.pexapp.data.source.api.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PhotoResponse(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    @SerializedName("photographer_url")
    val photographerUrl: String,
    @SerializedName("photographer_id")
    val photographerId: Int,
    @SerializedName("avg_color")
    val avgColor: String,
    val src: PhotoFeaturesResponse,
    val liked: Boolean,
    val alt: String
): Serializable

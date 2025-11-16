package com.example.pexapp.data.source.api.model

import com.google.gson.annotations.SerializedName

data class CollectionsResponseModelData(
    val id: String?,
    val title: String?,
    val description: String?,
    val private: String?,
    @SerializedName("media_count")
    val mediaCount: String?,
    @SerializedName("photos_count")
    val photosCount: String?,
    @SerializedName("videos_count")
    val videosCount: String?
)

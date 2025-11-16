package com.example.pexapp.data.source.api.model

import com.google.gson.annotations.SerializedName

data class PhotosResponseModelData(
    @SerializedName("total_results")
    val totalResults: String?,
    val page: String?,
    @SerializedName("per_page")
    val perPage: String?,
    val photos: List<PhotoResponseModelData?>?,
    @SerializedName("next_page")
    val nextPage: String?
)
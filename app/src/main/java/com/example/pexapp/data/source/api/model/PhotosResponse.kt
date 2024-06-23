package com.example.pexapp.data.source.api.model

import com.google.gson.annotations.SerializedName

data class PhotosResponse(
    @SerializedName("total_results")
    val totalResults: Int,
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    val photos: List<PhotoResponse>,
    @SerializedName("next_page")
    val nextPage: String?
)
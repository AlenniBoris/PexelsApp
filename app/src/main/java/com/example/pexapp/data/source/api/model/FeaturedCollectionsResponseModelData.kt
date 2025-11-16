package com.example.pexapp.data.source.api.model

import com.google.gson.annotations.SerializedName

data class FeaturedCollectionsResponseModelData(
    val collections: List<CollectionsResponseModelData?>?,
    val page: String?,
    @SerializedName("per_page")
    val perPage: String?,
    @SerializedName("total_results")
    val totalResults: String?,
    @SerializedName("prev_page")
    val prevPage: String?,
    @SerializedName("next_page")
    val nextPage: String?
)

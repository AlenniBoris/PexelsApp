package com.example.pexapp.responses

import com.example.pexapp.entity.Collections

data class FeaturedCollectionsResponse(
    val collections: List<Collections>,
    val page: Int,
    val per_page: Int,
    val total_results: Int,
    val prev_page: Int,
    val next_page: Int
)

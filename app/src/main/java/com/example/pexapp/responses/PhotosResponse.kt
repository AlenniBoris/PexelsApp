package com.example.pexapp.responses

import com.example.pexapp.entity.Photo

data class PhotosResponse(
    val total_results: Int,
    val page: Int,
    val per_page: Int,
    val photos: List<Photo>,
    val next_page: String?
)
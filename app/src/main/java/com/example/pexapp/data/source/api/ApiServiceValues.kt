package com.example.pexapp.data.source.api

object ApiServiceValues {
    const val BASE_URL = "https://api.pexels.com/v1/"
    const val HEADER_AUTHORIZATION = "Authorization"
    const val REQUEST_PHOTO_BY_ID = "photos/{id}"
    const val FIELD_ID = "id"
    const val REQUEST_CURATED_PHOTOS = "curated"
    const val FIELD_PER_PAGE = "per_page"
    const val FIELD_PAGE = "page"
    const val REQUEST_SEARCH = "search"
    const val FIELD_QUERY = "query"
    const val REQUEST_FEATURED_COLLECTIONS = "collections/featured"
    const val BASE_NUMBER_FEATURED_COLLECTIONS = 7
    const val BASE_NUMBER_PER_PAGE = 30
    const val BASE_PAGE_NUMBER = 1
}
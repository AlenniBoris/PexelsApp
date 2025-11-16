package com.example.pexapp.domain.repository

import com.example.pexapp.domain.model.CollectionsModelDomain
import com.example.pexapp.domain.model.CommonExceptionModelDomain
import com.example.pexapp.domain.model.CustomResultModelDomain
import com.example.pexapp.domain.model.PhotoModelDomain

interface IWebDataRepository {
    suspend fun getPhotoById(
        id: Int
    ): CustomResultModelDomain<PhotoModelDomain?, CommonExceptionModelDomain>

    suspend fun getCuratedPhotosList(
        page: Int? = null,
        perPage: Int? = null
    ): CustomResultModelDomain<List<PhotoModelDomain>, CommonExceptionModelDomain>

    suspend fun getSearchedPhotosList(
        query: String,
        page: Int? = null,
        perPage: Int? = null
    ): CustomResultModelDomain<List<PhotoModelDomain>, CommonExceptionModelDomain>

    suspend fun getFeaturedCollectionsList(): CustomResultModelDomain<List<CollectionsModelDomain>, CommonExceptionModelDomain>
}
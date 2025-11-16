package com.example.pexapp.domain.repository

import com.example.pexapp.domain.model.CommonExceptionModelDomain
import com.example.pexapp.domain.model.CustomResultModelDomain
import com.example.pexapp.domain.model.PhotoSimpleModelDomain
import kotlinx.coroutines.flow.Flow

interface IDatabaseRepository {
    suspend fun addPhoto(photo: PhotoSimpleModelDomain): CustomResultModelDomain<Unit, CommonExceptionModelDomain>

    suspend fun deletePhoto(photo: PhotoSimpleModelDomain): CustomResultModelDomain<Unit, CommonExceptionModelDomain>

    fun getAllFavourites(): Flow<List<PhotoSimpleModelDomain>>

    suspend fun getFavouriteById(id: Int): CustomResultModelDomain<PhotoSimpleModelDomain, CommonExceptionModelDomain>
}
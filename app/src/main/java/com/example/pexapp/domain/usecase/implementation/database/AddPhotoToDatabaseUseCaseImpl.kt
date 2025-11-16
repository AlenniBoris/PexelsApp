package com.example.pexapp.domain.usecase.implementation.database

import com.example.pexapp.domain.model.CommonExceptionModelDomain
import com.example.pexapp.domain.model.CustomResultModelDomain
import com.example.pexapp.domain.model.PhotoSimpleModelDomain
import com.example.pexapp.domain.repository.IDatabaseRepository
import com.example.pexapp.domain.usecase.logic.database.IAddPhotoToDatabaseUseCase
import com.example.pexapp.domain.util.IAppDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddPhotoToDatabaseUseCaseImpl @Inject constructor(
    private val databaseRepository: IDatabaseRepository,
    private val dispatchers: IAppDispatchers
) : IAddPhotoToDatabaseUseCase {
    override suspend fun invoke(
        photo: PhotoSimpleModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext databaseRepository.addPhoto(photo)
        }
}
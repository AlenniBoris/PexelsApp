package com.example.pexapp.domain.usecase.implementation.web

import com.example.pexapp.domain.model.CommonExceptionModelDomain
import com.example.pexapp.domain.model.CustomResultModelDomain
import com.example.pexapp.domain.model.PhotoModelDomain
import com.example.pexapp.domain.repository.IWebDataRepository
import com.example.pexapp.domain.usecase.logic.web.IGetPhotoByIdUseCase
import com.example.pexapp.domain.util.IAppDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPhotoByIdUseCaseImpl @Inject constructor(
    private val webDataRepository: IWebDataRepository,
    private val dispatchers: IAppDispatchers
) : IGetPhotoByIdUseCase {
    override suspend fun invoke(
        id: Int
    ): CustomResultModelDomain<PhotoModelDomain?, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext webDataRepository.getPhotoById(id = id)
        }
}
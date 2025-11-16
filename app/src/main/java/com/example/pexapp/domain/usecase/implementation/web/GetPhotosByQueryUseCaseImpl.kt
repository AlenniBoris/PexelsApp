package com.example.pexapp.domain.usecase.implementation.web

import com.example.pexapp.domain.model.CommonExceptionModelDomain
import com.example.pexapp.domain.model.CustomResultModelDomain
import com.example.pexapp.domain.model.PhotoModelDomain
import com.example.pexapp.domain.repository.IWebDataRepository
import com.example.pexapp.domain.usecase.logic.web.IGetPhotosByQueryUseCase
import com.example.pexapp.domain.util.IAppDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPhotosByQueryUseCaseImpl @Inject constructor(
    private val webDataRepository: IWebDataRepository,
    private val dispatchers: IAppDispatchers
) : IGetPhotosByQueryUseCase {
    override suspend fun invoke(
        query: String,
        page: Int?,
        perPage: Int?
    ): CustomResultModelDomain<List<PhotoModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext webDataRepository.getSearchedPhotosList(
                query = query,
                page = page,
                perPage = perPage
            )
        }
}
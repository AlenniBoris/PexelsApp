package com.example.pexapp.di

import com.example.pexapp.domain.usecase.implementation.database.AddPhotoToDatabaseUseCaseImpl
import com.example.pexapp.domain.usecase.implementation.database.DeletePhotoFromDatabaseUseCaseImpl
import com.example.pexapp.domain.usecase.implementation.database.GetLikedPhotoFromDatabaseByIdUseCaseImpl
import com.example.pexapp.domain.usecase.implementation.database.GetLikedPhotosFromDatabaseUseCaseImpl
import com.example.pexapp.domain.usecase.implementation.web.GetCuratedPhotosUseCaseImpl
import com.example.pexapp.domain.usecase.implementation.web.GetFeaturedCollectionsUseCaseImpl
import com.example.pexapp.domain.usecase.implementation.web.GetPhotoByIdUseCaseImpl
import com.example.pexapp.domain.usecase.implementation.web.GetPhotosByQueryUseCaseImpl
import com.example.pexapp.domain.usecase.logic.database.IAddPhotoToDatabaseUseCase
import com.example.pexapp.domain.usecase.logic.database.IDeletePhotoFromDatabaseUseCase
import com.example.pexapp.domain.usecase.logic.database.IGetLikedPhotoFromDatabaseByIdUseCase
import com.example.pexapp.domain.usecase.logic.database.IGetLikedPhotosFromDatabaseUseCase
import com.example.pexapp.domain.usecase.logic.web.IGetCuratedPhotosUseCase
import com.example.pexapp.domain.usecase.logic.web.IGetFeaturedCollectionsUseCase
import com.example.pexapp.domain.usecase.logic.web.IGetPhotoByIdUseCase
import com.example.pexapp.domain.usecase.logic.web.IGetPhotosByQueryUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun bindAddPhotoToDatabaseUseCase(
        addPhotoToDatabaseUseCaseImpl: AddPhotoToDatabaseUseCaseImpl
    ): IAddPhotoToDatabaseUseCase

    @Binds
    abstract fun bindDeletePhotoFromDatabaseUseCase(
        deletePhotoFromDatabaseUseCaseImpl: DeletePhotoFromDatabaseUseCaseImpl
    ): IDeletePhotoFromDatabaseUseCase

    @Binds
    abstract fun bindGetLikedPhotoFromDatabaseByIdUseCase(
        getLikedPhotoFromDatabaseByIdUseCaseImpl: GetLikedPhotoFromDatabaseByIdUseCaseImpl
    ): IGetLikedPhotoFromDatabaseByIdUseCase

    @Binds
    abstract fun bindGetLikedPhotosFromDatabaseUseCase(
        getLikedPhotosFromDatabaseUseCaseImpl: GetLikedPhotosFromDatabaseUseCaseImpl
    ): IGetLikedPhotosFromDatabaseUseCase

    @Binds
    abstract fun bindGetCuratedPhotosUseCase(
        getCuratedPhotosUseCaseImpl: GetCuratedPhotosUseCaseImpl
    ): IGetCuratedPhotosUseCase

    @Binds
    abstract fun bindGetFeaturedCollectionsUseCase(
        getFeaturedCollectionsUseCaseImpl: GetFeaturedCollectionsUseCaseImpl
    ): IGetFeaturedCollectionsUseCase

    @Binds
    abstract fun bindGetPhotoByIdUseCase(
        getPhotoByIdUseCaseImpl: GetPhotoByIdUseCaseImpl
    ): IGetPhotoByIdUseCase

    @Binds
    abstract fun bindGetPhotosByQueryUseCase(
        getPhotosByQueryUseCaseImpl: GetPhotosByQueryUseCaseImpl
    ): IGetPhotosByQueryUseCase
}
package com.example.pexapp.presentation.model

import com.example.pexapp.domain.model.PhotoModelDomain
import com.example.pexapp.domain.model.PhotoSimpleModelDomain

data class PhotoModelUi(
    val domainModel: PhotoModelDomain?,
    val simpleModel: PhotoSimpleModelDomain?
)

fun PhotoModelDomain.toUiModel(): PhotoModelUi =
    PhotoModelUi(
        domainModel = this,
        simpleModel = this.toSimpleModel()
    )

fun PhotoModelDomain.toSimpleModel(): PhotoSimpleModelDomain =
    PhotoSimpleModelDomain(
        id = this.id,
        photographer = this.photographer,
        photoPictureUrl = this.src.medium
    )

fun PhotoSimpleModelDomain.toUiModel(): PhotoModelUi =
    PhotoModelUi(
        domainModel = null,
        simpleModel = this
    )
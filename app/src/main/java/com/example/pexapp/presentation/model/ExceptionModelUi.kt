package com.example.pexapp.presentation.model

import com.example.pexapp.domain.model.CommonExceptionModelDomain
import com.example.pexapp.presentation.mappers.toUiPicture
import com.example.pexapp.presentation.mappers.toUiString

data class ExceptionModelUi(
    val exceptionIconResource: Int,
    val exceptionStringResource: Int
)

fun CommonExceptionModelDomain.toUiModel(): ExceptionModelUi =
    ExceptionModelUi(
        exceptionIconResource = this.toUiPicture(),
        exceptionStringResource = this.toUiString()
    )

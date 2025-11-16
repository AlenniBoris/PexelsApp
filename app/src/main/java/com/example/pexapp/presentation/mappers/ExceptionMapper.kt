package com.example.pexapp.presentation.mappers

import com.example.pexapp.R
import com.example.pexapp.domain.model.CommonExceptionModelDomain

fun CommonExceptionModelDomain.toUiString(): Int = when (this) {
    CommonExceptionModelDomain.ErrorGettingData -> R.string.error_getting_data_exception
    CommonExceptionModelDomain.InternetException -> R.string.error_internet
    CommonExceptionModelDomain.UnknownException -> R.string.error_unknown
}

fun CommonExceptionModelDomain.toUiPicture(): Int = when (this) {
    CommonExceptionModelDomain.InternetException -> R.drawable.network_exception_icon
    CommonExceptionModelDomain.UnknownException, CommonExceptionModelDomain.ErrorGettingData ->
        R.drawable.unknown_exception_icon
}
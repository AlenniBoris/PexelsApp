package com.example.pexapp.domain.model

sealed class CommonExceptionModelDomain : Throwable() {
    data object InternetException : CommonExceptionModelDomain()
    data object UnknownException : CommonExceptionModelDomain()
    data object ErrorGettingData : CommonExceptionModelDomain()
}


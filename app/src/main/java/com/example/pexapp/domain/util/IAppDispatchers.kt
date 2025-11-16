package com.example.pexapp.domain.util

import kotlinx.coroutines.CoroutineDispatcher

interface IAppDispatchers {
    val Main: CoroutineDispatcher
    val IO: CoroutineDispatcher
    val Default: CoroutineDispatcher
}
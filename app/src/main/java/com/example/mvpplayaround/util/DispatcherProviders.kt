package com.example.mvpplayaround.util

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProviders {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}
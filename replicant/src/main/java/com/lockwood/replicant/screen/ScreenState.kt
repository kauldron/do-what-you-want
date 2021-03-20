package com.lockwood.replicant.screen

sealed class ScreenState

object Loading : ScreenState()

object Content : ScreenState()

object Stub : ScreenState()

object Error : ScreenState()

object NoInternetConnection : ScreenState()

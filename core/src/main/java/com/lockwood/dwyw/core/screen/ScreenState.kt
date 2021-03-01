package com.lockwood.dwyw.core.screen

sealed class ScreenState

object Loading : ScreenState()
object Content : ScreenState()
object Stub : ScreenState()
object Error : ScreenState()
object NoInternetConnection : ScreenState()
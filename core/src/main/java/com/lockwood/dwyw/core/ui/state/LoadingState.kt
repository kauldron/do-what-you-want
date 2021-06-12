package com.lockwood.dwyw.core.ui.state

sealed class LoadingState {
    object Loading : LoadingState()
    object Content : LoadingState()
    object Stub : LoadingState()
}

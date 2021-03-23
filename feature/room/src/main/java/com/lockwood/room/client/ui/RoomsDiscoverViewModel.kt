package com.lockwood.room.client.ui

import android.os.Handler
import android.os.Looper
import com.lockwood.dwyw.core.BaseViewModel
import com.lockwood.dwyw.core.screen.RoomScreen
import com.lockwood.room.data.Room
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.screen.RetryErrorScreen

internal class RoomsDiscoverViewModel(
  private val roomsInteractor: IRoomsInteractor,
) : BaseViewModel<RoomsDiscoverViewState>(RoomsDiscoverViewState.initialStateDiscover) {

  private companion object {

    private const val DEFAULT_LOADING_DELAY = 500L
  }

  fun startDiscoveryRooms() {
    mutateState { state.copy(isLoading = true) }

    // post delay to get around of screen blinking
    postDelay {
      roomsInteractor
        .startDiscovery()
        .addOnCompleteListener { mutateState { state.copy(isLoading = false) } }
        .addOnFailureListener { navigateTo(RetryErrorScreen) }
    }
  }

  fun navigateToRoom(item: Room) {
    navigateTo(RoomScreen(item.id))
  }

  private fun postDelay(action: Runnable) {
    Handler((Looper.getMainLooper())).postDelayed(action, DEFAULT_LOADING_DELAY)
  }
}

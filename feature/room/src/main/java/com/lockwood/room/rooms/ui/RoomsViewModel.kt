package com.lockwood.room.rooms.ui

import com.lockwood.dwyw.core.BaseViewModel
import com.lockwood.dwyw.core.screen.RoomScreen
import com.lockwood.replicant.event.MessageEvent
import com.lockwood.room.data.Room
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.screen.RetryErrorScreen

internal class RoomsViewModel(
  private val roomsInteractor: IRoomsInteractor,
) : BaseViewModel<RoomsViewState>(RoomsViewState.initialState) {

  fun startDiscoveryRooms() {
    mutateState { state.copy(isLoading = true) }

    roomsInteractor
      .startDiscovery()
      .addOnCompleteListener { mutateState { state.copy(isLoading = false) } }
      .addOnFailureListener { navigateTo(RetryErrorScreen) }
  }

  fun startAdvertisingRoom(name: String) {
    mutateState { state.copy(isLoading = true) }

    roomsInteractor
      .startAdvertising(name)
      .addOnCompleteListener { mutateState { state.copy(isLoading = false) } }
      .addOnFailureListener { offerEvent { MessageEvent("Failed to create $name\n${it.message}") } }
      .addOnSuccessListener {
        // showNotification
      }
  }

  fun navigateToRoom(item: Room) {
    navigateTo(RoomScreen(item.id))
  }
}

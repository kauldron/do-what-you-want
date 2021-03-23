package com.lockwood.room.rooms.ui

import com.lockwood.dwyw.core.BaseViewModel
import com.lockwood.dwyw.core.screen.RoomScreen
import com.lockwood.replicant.event.MessageEvent
import com.lockwood.room.data.Room
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.event.StartHostServiceEvent
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

    // TODO: Check if already advertising
    roomsInteractor
      .startAdvertising(name)
      .addOnCompleteListener { mutateState { state.copy(isLoading = false) } }
      .addOnFailureListener { offerEvent { MessageEvent("Failed to create $name\n${it.message}") } }
      .addOnSuccessListener {
        offerEvent { StartHostServiceEvent }
        // TODO: navigateTo Hosting Start Screen
        // navigateTo()
      }
  }

  fun navigateToRoom(item: Room) {
    navigateTo(RoomScreen(item.id))
  }
}

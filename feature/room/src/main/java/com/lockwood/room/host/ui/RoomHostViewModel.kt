package com.lockwood.room.host.ui

import com.lockwood.dwyw.core.BaseViewModel
import com.lockwood.replicant.event.MessageEvent
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.event.StartHostServiceEvent

internal class RoomHostViewModel(
  private val roomsInteractor: IRoomsInteractor,
) : BaseViewModel<RoomHostViewState>(RoomHostViewState.initialStateHost) {

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
}

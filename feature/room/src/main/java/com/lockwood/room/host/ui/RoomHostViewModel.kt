package com.lockwood.room.host.ui

import com.lockwood.dwyw.core.BaseViewModel
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.event.StartHostServiceEvent
import com.lockwood.room.event.StopHostServiceEvent

internal class RoomHostViewModel(
  private val roomsInteractor: IRoomsInteractor,
) : BaseViewModel<RoomHostViewState>(RoomHostViewState.initialState) {

  fun startBroadcasting() {
    mutateState { state.copy(isEnabled = true, isSharing = true) }
    offerEvent { StartHostServiceEvent }
  }

  fun stopBroadcasting() {
    mutateState { state.copy(isEnabled = true, isSharing = false) }
    offerEvent { StopHostServiceEvent }
  }
}

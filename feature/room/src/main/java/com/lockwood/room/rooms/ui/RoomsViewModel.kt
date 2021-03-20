package com.lockwood.room.rooms.ui

import com.lockwood.dwyw.core.BaseViewModel
import com.lockwood.dwyw.core.screen.RoomScreen
import com.lockwood.room.data.Room
import com.lockwood.room.data.interactor.IRoomsInteractor

internal class RoomsViewModel(
  private val roomsInteractor: IRoomsInteractor,
) : BaseViewModel<RoomsViewState>(RoomsViewState.initialState) {

  fun fetchRooms() {
    val roomsList = roomsInteractor.fetchRooms().toTypedArray()
    mutateState { state.copy(rooms = roomsList, isLoading = false) }
  }

  fun navigateToRoom(item: Room) {
    navigateTo(RoomScreen(item.id))
  }
}

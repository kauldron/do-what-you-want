package com.lockwood.room.viewmodel

import com.lockwood.dwyw.core.BaseViewModel
import com.lockwood.dwyw.core.screen.RoomScreen
import com.lockwood.room.data.IRoomsInteractor
import com.lockwood.room.data.Room
import com.lockwood.room.ui.state.RoomsViewState

class RoomsViewModel(
    private val roomsInteractor: IRoomsInteractor
) : BaseViewModel<RoomsViewState>(RoomsViewState.initialState) {

    fun fetchRooms() {
        val roomsList = roomsInteractor.fetchRooms().toTypedArray()
        mutateState { state.copy(rooms = roomsList, isLoading = false) }
    }

    fun navigateToRoom(item: Room) {
        navigateTo(RoomScreen(item.id))
    }

}
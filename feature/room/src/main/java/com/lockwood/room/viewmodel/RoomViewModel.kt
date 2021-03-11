package com.lockwood.room.viewmodel

import com.lockwood.dwyw.core.BaseViewModel
import com.lockwood.room.data.IRoomsInteractor
import com.lockwood.room.ui.state.RoomViewState

class RoomViewModel(
    private val roomsInteractor: IRoomsInteractor
) : BaseViewModel<RoomViewState>(RoomViewState.initialState) {

    fun connectToRoom(roomId: Int) {
        val room = roomsInteractor.fetchRoom(roomId)
        if (room == null) {
            mutateState { state.copy(isLoading = false) }
            showErrorMessage("Room with id: $roomId not found")
        } else {
            mutateState { state.copy(room = room, isLoading = false) }
            // TODO: Remove message
            showMessage("Room with id: $roomId found")
        }
    }

}
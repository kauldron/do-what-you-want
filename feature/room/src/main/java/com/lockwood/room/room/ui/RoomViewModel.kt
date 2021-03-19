package com.lockwood.room.room.ui

import com.lockwood.dwyw.core.BaseViewModel
import com.lockwood.replicant.event.ErrorMessageEvent
import com.lockwood.replicant.event.MessageEvent
import com.lockwood.room.data.interactor.IRoomsInteractor

internal class RoomViewModel(
		private val roomsInteractor: IRoomsInteractor,
) : BaseViewModel<RoomViewState>(RoomViewState.initialState) {

	fun connectToRoom(roomId: Int) {
		val room = roomsInteractor.fetchRoom(roomId)
		if (room == null) {
			mutateState { state.copy(isLoading = false) }
			offerEvent { ErrorMessageEvent("Room with id: $roomId not found") }
		} else {
			mutateState { state.copy(room = room, isLoading = false) }
			// TODO: Remove message
			offerEvent { MessageEvent("Room with id: $roomId found") }
		}
	}

}
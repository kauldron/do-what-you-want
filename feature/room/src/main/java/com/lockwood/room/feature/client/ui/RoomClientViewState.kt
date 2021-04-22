package com.lockwood.room.feature.client.ui

import com.lockwood.replicant.state.ViewState
import com.lockwood.room.model.Room

internal data class RoomClientViewState(
		val room: Room,
		val isEnabled: Boolean,
		val isConnected: Boolean,
) : ViewState {

	companion object {

		val initialState: RoomClientViewState
			@JvmStatic
			get() {
				return RoomClientViewState(
						room = Room.EMPTY,
						isEnabled = false,
						isConnected = false
				)
			}
	}

	override fun toString(): String {
		return super.toString()
	}

}

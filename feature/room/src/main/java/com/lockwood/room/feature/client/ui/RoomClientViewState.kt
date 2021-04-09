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

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as RoomClientViewState

		if (room != other.room) return false
		if (isEnabled != other.isEnabled) return false
		if (isConnected != other.isConnected) return false

		return true
	}

	override fun hashCode(): Int {
		var result = room.hashCode()
		result = 31 * result + isEnabled.hashCode()
		result = 31 * result + isConnected.hashCode()
		return result
	}

	override fun toString(): String {
		return super.toString()
	}

}

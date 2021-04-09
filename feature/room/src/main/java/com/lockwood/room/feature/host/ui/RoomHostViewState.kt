package com.lockwood.room.feature.host.ui

import com.lockwood.replicant.state.ViewState

internal data class RoomHostViewState(
		val isEnabled: Boolean,
		val isSharing: Boolean,
) : ViewState {

	companion object {

		val initialState: RoomHostViewState
			@JvmStatic
			get() {
				return RoomHostViewState(isEnabled = false, isSharing = false)
			}
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as RoomHostViewState

		if (isEnabled != other.isEnabled) return false
		if (isSharing != other.isSharing) return false

		return true
	}

	override fun hashCode(): Int {
		var result = isEnabled.hashCode()
		result = 31 * result + isSharing.hashCode()
		return result
	}

	override fun toString(): String {
		return super.toString()
	}

}

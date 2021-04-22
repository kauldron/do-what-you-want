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

	override fun toString(): String {
		return super.toString()
	}

}

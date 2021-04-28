package com.lockwood.room.feature.discover.ui

import com.lockwood.dwyw.core.ui.state.LoadingState
import com.lockwood.replicant.state.ViewState
import com.lockwood.room.feature.discover.model.RoomsArray

internal data class RoomsDiscoverViewState(
		val rooms: RoomsArray,
		val isLoading: LoadingState,
) : ViewState {

	companion object {

		val initialState: RoomsDiscoverViewState
			@JvmStatic
			get() {
				return RoomsDiscoverViewState(
						rooms = RoomsArray(emptyArray()),
						isLoading = LoadingState(true),
				)
			}
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as RoomsDiscoverViewState

		if (!rooms.value.contentEquals(other.rooms.value)) return false
		if (isLoading != other.isLoading) return false

		return true
	}

	override fun hashCode(): Int {
		var result = rooms.value.contentHashCode()
		result = 31 * result + isLoading.hashCode()
		return result
	}

	override fun toString(): String {
		return super.toString()
	}
}

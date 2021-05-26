package com.lockwood.room.feature.client.ui

import android.os.Parcelable
import com.lockwood.room.model.Room
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class RoomClientViewState(
		@JvmField
		val room: Room,
		@JvmField
		val isEnabled: Boolean,
		@JvmField
		val isConnected: Boolean,
) : Parcelable {

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

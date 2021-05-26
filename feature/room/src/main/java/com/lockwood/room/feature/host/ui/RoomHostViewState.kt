package com.lockwood.room.feature.host.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class RoomHostViewState(
		@JvmField
		val isEnabled: Boolean,
		@JvmField
		val isSharing: Boolean,
) : Parcelable {

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

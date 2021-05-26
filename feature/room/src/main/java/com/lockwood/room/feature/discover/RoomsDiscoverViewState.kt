package com.lockwood.room.feature.discover

import android.os.Bundle
import android.os.Parcelable
import com.lockwood.dwyw.core.ui.state.LoadingState
import com.lockwood.room.model.Room
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
internal data class RoomsDiscoverViewState(
		@JvmField
		val rooms: List<Room>,
		@JvmField
		val loadingState: @RawValue LoadingState,
) : Parcelable {

	companion object {

		private val TAG = RoomsDiscoverViewState::class.simpleName

		val initialState: RoomsDiscoverViewState
			@JvmStatic
			get() {
				return RoomsDiscoverViewState(
						rooms = emptyList(),
						loadingState = LoadingState.Loading,
				)
			}

		@JvmStatic
		fun fromBundleOrDefault(bundle: Bundle?): RoomsDiscoverViewState {
			return bundle?.getParcelable(TAG) ?: initialState
		}

		@JvmStatic
		fun toBundle(bundle: Bundle, value: RoomsDiscoverViewState) {
			bundle.putParcelable(TAG, value)
		}

	}

	override fun toString(): String {
		return super.toString()
	}

}

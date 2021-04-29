package com.lockwood.room.feature.discover.ui

import com.lockwood.dwyw.core.ui.state.LoadingState
import com.lockwood.replicant.transform.StateTransformer
import com.lockwood.room.feature.discover.model.RoomsArray
import timber.log.Timber

internal class RoomsDiscoverStateTransformer : StateTransformer<RoomsDiscoverViewState> {

	// TODO: Replace with reducer
	override fun <T> accept(
			data: T,
			state: RoomsDiscoverViewState
	): RoomsDiscoverViewState {
		Timber.d("acceptTransform: $data to $state")
		return when (data) {
			is LoadingState -> state.copy(isLoading = data)
			is RoomsArray -> state.copy(rooms = data)
			else -> error("Unknown data ($data) for state ($state)")
		}
	}

}
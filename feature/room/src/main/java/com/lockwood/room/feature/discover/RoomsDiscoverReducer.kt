package com.lockwood.room.feature.discover

import com.lockwood.dwyw.core.ui.state.LoadingState.Content
import com.lockwood.dwyw.core.ui.state.LoadingState.Loading
import com.lockwood.dwyw.core.ui.state.LoadingState.Stub
import com.lockwood.replicant.mvi.Reducer
import com.lockwood.replicant.mvi.listOfNothing
import com.lockwood.room.feature.discover.RoomsDiscoveryAction.AcceptConnection
import com.lockwood.room.feature.discover.RoomsDiscoveryAction.AddRoom
import com.lockwood.room.feature.discover.RoomsDiscoveryAction.AddRooms
import com.lockwood.room.feature.discover.RoomsDiscoveryAction.DiscoverRooms
import com.lockwood.room.feature.discover.RoomsDiscoveryAction.Idle
import com.lockwood.room.feature.discover.RoomsDiscoveryAction.NavigateToScreen
import com.lockwood.room.feature.discover.RoomsDiscoveryAction.RejectConnection
import com.lockwood.room.feature.discover.RoomsDiscoveryAction.RemoveRoom
import com.lockwood.room.feature.discover.RoomsDiscoveryAction.RequestConnection
import com.lockwood.room.feature.discover.RoomsDiscoveryEffects.ShowAcceptConnection
import com.lockwood.room.feature.discover.RoomsDiscoveryEffects.ShowScreen
import com.lockwood.room.model.Room

internal class RoomsDiscoverReducer :
		Reducer<RoomsDiscoverViewState, RoomsDiscoveryAction, RoomsDiscoveryEffects> {

	override fun invoke(
			action: RoomsDiscoveryAction,
			state: RoomsDiscoverViewState,
	): Pair<RoomsDiscoverViewState, List<RoomsDiscoveryEffects>> {
		val newState = handleActionState(action, state)
		val effects = handleActionEffects(action)
		return newState to effects
	}

	private fun handleActionState(
			action: RoomsDiscoveryAction,
			state: RoomsDiscoverViewState,
	): RoomsDiscoverViewState = when (action) {
		is Idle -> state.handleStateByRoomsCount()
		is DiscoverRooms, is RequestConnection -> state.copy(loadingState = Loading)
		is RejectConnection, is AcceptConnection -> state.copy(loadingState = Content)

		is AddRoom -> {
			val result = state.rooms.mutate { it.add(action.room) }

			state.copy(rooms = result, loadingState = Content)
		}
		is AddRooms -> {
			val result = state.rooms.mutate { it.addAll(action.rooms) }

			state.copy(rooms = result, loadingState = Content)
		}
		is RemoveRoom -> {
			val result = state.rooms.mutate { it.filterNot { room -> room.endpointId == action.endpointId } }

			state.copy(rooms = result).handleStateByRoomsCount()
		}
		else -> state
	}

	private fun RoomsDiscoverViewState.handleStateByRoomsCount(): RoomsDiscoverViewState {
		return if (rooms.isNullOrEmpty()) {
			copy(loadingState = Stub)
		} else {
			copy(loadingState = Content)
		}
	}

	private fun handleActionEffects(
			action: RoomsDiscoveryAction
	): List<RoomsDiscoveryEffects> = when (action) {
		is NavigateToScreen -> listOf(ShowScreen(action.screen))
		is AcceptConnection -> listOf(ShowAcceptConnection(action.room))
		else -> listOfNothing()
	}

	private inline fun List<Room>.mutate(
			mutate: (MutableList<Room>) -> Unit
	): List<Room> = toMutableList()
			.apply(mutate)
			.sortedBy(Room::name)
			.toList()

}

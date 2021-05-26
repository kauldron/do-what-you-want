package com.lockwood.room.feature.discover.middleware

import com.lockwood.automata.core.notSafeLazy
import com.lockwood.connections.INearbyConnectionsManager
import com.lockwood.connections.callback.DiscoveryCallback
import com.lockwood.connections.model.EndpointId
import com.lockwood.connections.model.EndpointInfo
import com.lockwood.replicant.mvi.base.BaseMiddleWare
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.feature.discover.RoomsDiscoverViewState
import com.lockwood.room.feature.discover.RoomsDiscoveryAction
import com.lockwood.room.feature.discover.RoomsDiscoveryAction.AddRoom
import com.lockwood.room.feature.discover.RoomsDiscoveryAction.DiscoverRooms
import com.lockwood.room.feature.discover.RoomsDiscoveryAction.Idle
import com.lockwood.room.feature.discover.RoomsDiscoveryAction.RemoveRoom
import com.lockwood.room.model.Room

internal class RoomsDiscoveryMiddleware(
		@JvmField
		private val roomsInteractor: IRoomsInteractor,
		@JvmField
		private val connectionsManager: INearbyConnectionsManager,
) : BaseMiddleWare<RoomsDiscoverViewState, RoomsDiscoveryAction>() {

	private val discoveryCallback: Lazy<DiscoveryCallback> = notSafeLazy {
		object : DiscoveryCallback {
			override fun onEndpointFound(endpointId: EndpointId, info: EndpointInfo) {
				val room = Room(endpointId = endpointId, name = info.name)

				// workaround for duplicate advertising
				if (roomsInteractor.isSameHostRoom(room)) {
					roomsInteractor.stopAdvertising()
				} else {
					accept(AddRoom(room))
				}
			}

			override fun onEndpointLost(endpointId: EndpointId) {
				accept(RemoveRoom(endpointId))
			}
		}
	}

	override fun accept(action: RoomsDiscoveryAction) = when (action) {
		is DiscoverRooms -> startDiscovery()
		else -> Unit
	}

	override fun release() {
		removeDiscoveryCallback()
	}

	private fun startDiscovery() {
		addDiscoveryCallback()

		roomsInteractor
				.startDiscovery()
				.addOnCompleteListener { store.accept(Idle) }
	}

	private fun addDiscoveryCallback() {
		if (!discoveryCallback.isInitialized()) {
			connectionsManager.addDiscoveryCallback(discoveryCallback.value)
		}
	}

	private fun removeDiscoveryCallback() {
		connectionsManager.removeDiscoveryCallback(discoveryCallback.value)
	}

}
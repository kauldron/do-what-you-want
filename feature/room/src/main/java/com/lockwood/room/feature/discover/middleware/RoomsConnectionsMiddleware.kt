package com.lockwood.room.feature.discover.middleware

import com.lockwood.automata.core.notSafeLazy
import com.lockwood.connections.INearbyConnectionsManager
import com.lockwood.connections.callback.ConnectionCallback
import com.lockwood.connections.model.ConnectionInfo
import com.lockwood.connections.model.ConnectionSuccess
import com.lockwood.connections.model.ConnectionsStatus
import com.lockwood.connections.model.EndpointId
import com.lockwood.replicant.mvi.base.BaseMiddleWare
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.feature.discover.RoomsDiscoverViewState
import com.lockwood.room.feature.discover.RoomsDiscoveryAction
import com.lockwood.room.feature.discover.RoomsDiscoveryAction.AcceptConnection
import com.lockwood.room.feature.discover.RoomsDiscoveryAction.NavigateToScreen
import com.lockwood.room.feature.discover.RoomsDiscoveryAction.RejectConnection
import com.lockwood.room.feature.discover.RoomsDiscoveryAction.RequestConnection
import com.lockwood.room.feature.discover.RoomsDiscoveryAction.ShowMessage
import com.lockwood.room.model.Room
import com.lockwood.room.screen.ConnectionScreen

internal class RoomsConnectionsMiddleware(
		@JvmField
		private val roomsInteractor: IRoomsInteractor,
		@JvmField
		private val connectionsManager: INearbyConnectionsManager,
) : BaseMiddleWare<RoomsDiscoverViewState, RoomsDiscoveryAction>() {

	private val connectionCallback: Lazy<ConnectionCallback> = notSafeLazy {
		object : ConnectionCallback {
			override fun onConnectionInitiated(endpointId: EndpointId, connectionInfo: ConnectionInfo) {
				val room = Room(endpointId = endpointId, name = connectionInfo.endpointName)

				accept(AcceptConnection(room))
			}

			override fun onConnectionResult(endpointId: EndpointId, connectionStatus: ConnectionsStatus) {
				val action = when (connectionStatus) {
					is ConnectionSuccess -> NavigateToScreen(ConnectionScreen(roomsInteractor.connectedRoom))
					else -> ShowMessage("Connection result with $endpointId: $connectionStatus")
				}

				accept(action)
			}

			override fun onDisconnected(endpointId: EndpointId) {
				accept(ShowMessage("Disconnected from $endpointId"))
			}
		}
	}

	override fun accept(action: RoomsDiscoveryAction) = when (action) {
		is AcceptConnection -> acceptConnection(action.room)
		is RequestConnection -> requestConnection(action.room)
		is RejectConnection -> rejectConnection(action.room)
		else -> Unit
	}

	override fun release() {
		removeConnectionsCallback()
	}

	private fun requestConnection(item: Room) {
		addConnectionsCallback()
		with(roomsInteractor) {
			connectedRoom = item
			requestConnection(item.endpointId)
		}
	}

	private fun acceptConnection(item: Room) {
		roomsInteractor.acceptConnection(item.endpointId)
	}

	private fun rejectConnection(item: Room) {
		roomsInteractor.rejectConnection(item.endpointId)
	}

	private fun addConnectionsCallback() {
		if (!connectionCallback.isInitialized()) {
			connectionsManager.addConnectionCallback(connectionCallback.value)
		}
	}

	private fun removeConnectionsCallback() {
		connectionsManager.removeConnectionCallback(connectionCallback.value)
	}
}
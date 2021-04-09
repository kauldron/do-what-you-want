package com.lockwood.room.feature.host.ui

import com.lockwood.automata.core.notSafeLazy
import com.lockwood.connections.INearbyConnectionsManager
import com.lockwood.connections.callback.ConnectionCallback
import com.lockwood.connections.model.ConnectionInfo
import com.lockwood.connections.model.ConnectionsStatus
import com.lockwood.connections.model.EndpointId
import com.lockwood.replicant.event.MessageEvent
import com.lockwood.replicant.executor.ExecutorProvider
import com.lockwood.room.base.BaseConnectionViewModel
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.feature.discover.event.ShowAcceptConnectionEvent
import com.lockwood.room.feature.host.event.RequestCaptureEvent
import com.lockwood.room.feature.host.event.StartHostServiceEvent
import com.lockwood.room.feature.host.event.StopHostServiceEvent
import com.lockwood.room.model.Room

internal class RoomHostViewModel(
		roomsInteractor: IRoomsInteractor,
		connectionsManager: INearbyConnectionsManager,
		executorProvider: ExecutorProvider,
) : BaseConnectionViewModel<RoomHostViewState>(
		roomsInteractor = roomsInteractor,
		connectionsManager = connectionsManager,
		executorProvider = executorProvider,
		initState = RoomHostViewState.initialState
) {

	private val connectionCallback: Lazy<ConnectionCallback> = notSafeLazy {
		object : ConnectionCallback {
			override fun onConnectionInitiated(endpointId: EndpointId, connectionInfo: ConnectionInfo) {
				val room = Room(endpointId = endpointId, name = connectionInfo.endpointName)

				offerEvent { ShowAcceptConnectionEvent(room) }
			}

			override fun onConnectionResult(endpointId: EndpointId, connectionStatus: ConnectionsStatus) {
				offerEvent { MessageEvent("Connection result with $endpointId: $connectionStatus") }
			}

			override fun onDisconnected(endpointId: EndpointId) {
				offerEvent { MessageEvent("Some one disconnected from you") }
			}
		}
	}

	override fun onCleared() = with(connectionsManager) {
		removeConnectionCallback(connectionCallback.value)
		super.onCleared()
	}

	fun startBroadcasting() {
		addConnectionCallback(connectionCallback)

		offerEvent { StartHostServiceEvent }
		postDelay { offerEvent { RequestCaptureEvent } }
	}

	fun stopBroadcasting() {
		connectionsManager.removeConnectionCallback(connectionCallback.value)

		onStopBroadcasting()
		offerEvent { StopHostServiceEvent }
	}

	fun onStartBroadcasting() {
		mutateState { state.copy(isEnabled = true, isSharing = true) }
	}

	private fun onStopBroadcasting() {
		mutateState { state.copy(isEnabled = true, isSharing = false) }
	}

}

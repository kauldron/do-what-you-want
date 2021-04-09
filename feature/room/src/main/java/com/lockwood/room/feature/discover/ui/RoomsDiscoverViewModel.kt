package com.lockwood.room.feature.discover.ui

import com.lockwood.automata.core.notSafeLazy
import com.lockwood.connections.INearbyConnectionsManager
import com.lockwood.connections.callback.ConnectionCallback
import com.lockwood.connections.callback.DiscoveryCallback
import com.lockwood.connections.model.ConnectionInfo
import com.lockwood.connections.model.ConnectionSuccess
import com.lockwood.connections.model.ConnectionsStatus
import com.lockwood.connections.model.EndpointId
import com.lockwood.connections.model.EndpointInfo
import com.lockwood.replicant.event.Event
import com.lockwood.replicant.event.MessageEvent
import com.lockwood.replicant.event.ShowScreenEvent
import com.lockwood.replicant.executor.ExecutorProvider
import com.lockwood.room.base.BaseConnectionViewModel
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.feature.discover.event.ShowAcceptConnectionEvent
import com.lockwood.room.model.Room
import com.lockwood.room.screen.RoomConnectionScreen
import com.lockwood.room.screen.RoomsAdvertisingScreen

internal class RoomsDiscoverViewModel(
		roomsInteractor: IRoomsInteractor,
		connectionsManager: INearbyConnectionsManager,
		executorProvider: ExecutorProvider,
) : BaseConnectionViewModel<RoomsDiscoverViewState>(
		roomsInteractor = roomsInteractor,
		connectionsManager = connectionsManager,
		executorProvider = executorProvider,
		initState = RoomsDiscoverViewState.initialState
) {

	private val discoveryCallback: Lazy<DiscoveryCallback> = notSafeLazy {
		object : DiscoveryCallback {
			override fun onEndpointFound(endpointId: EndpointId, info: EndpointInfo) = with(info) {
				addRoom(Room(endpointId = endpointId, name = name))
			}

			override fun onEndpointLost(endpointId: EndpointId) {
				removeRoom(endpointId)
			}
		}
	}

	private val connectionCallback: Lazy<ConnectionCallback> = notSafeLazy {
		object : ConnectionCallback {
			override fun onConnectionInitiated(endpointId: EndpointId, connectionInfo: ConnectionInfo) {
				val room = Room(endpointId = endpointId, name = connectionInfo.endpointName)

				offerEvent { ShowAcceptConnectionEvent(room) }
			}

			override fun onConnectionResult(endpointId: EndpointId, connectionStatus: ConnectionsStatus) {
				mutateState { state.copy(isLoading = false) }

				val statusEvent = when (connectionStatus) {
					is ConnectionSuccess -> navigateToConnectedRoom()
					else -> MessageEvent("Connection result with $endpointId: $connectionStatus")
				}
				offerEvent { statusEvent }
			}

			override fun onDisconnected(endpointId: EndpointId) {
				mutateState { state.copy(isLoading = false) }

				offerEvent { MessageEvent("Disconnected from $endpointId") }
			}
		}
	}

	override fun onCleared() = with(connectionsManager) {
		removeDiscoveryCallback(discoveryCallback.value)
		removeConnectionCallback(connectionCallback.value)
		super.onCleared()
	}

	override fun requestConnection(item: Room) {
		mutateState { state.copy(isLoading = true) }

		addConnectionCallback(connectionCallback)

		roomsInteractor.connectedRoom = item
		super.requestConnection(item)
	}

	override fun acceptConnection(item: Room) {
		super.acceptConnection(item)

		mutateState { state.copy(isLoading = false) }
	}

	override fun rejectConnection(item: Room) {
		super.rejectConnection(item)

		mutateState { state.copy(isLoading = false) }
	}

	fun startDiscoveryRooms() {
		mutateState { state.copy(isLoading = true) }

		addDiscoveryCallback(discoveryCallback)

		// post delay to get around of screen blinking
		postDelay {
			roomsInteractor
					.startDiscovery()
					.addOnCompleteListener {
						// post delay to workaround of getting single endpoint with nearby api
						postDelay { mutateState { state.copy(isLoading = false) } }
					}
		}
	}

	fun navigateToAdvertising() {
		navigateTo(RoomsAdvertisingScreen)
	}

	private fun navigateToConnectedRoom(): Event {
		val connectionScreen = RoomConnectionScreen(roomsInteractor.connectedRoom)
		return ShowScreenEvent(connectionScreen)
	}

	private fun addRoom(room: Room) {
		val rooms = state.rooms
				.toMutableSet()
				.apply { add(room) }
				.sortedBy(Room::name)
				.toTypedArray()

		mutateState { state.copy(rooms = rooms) }
	}

	private fun removeRoom(endpointId: EndpointId) {
		val rooms = state.rooms
				.toList()
				.filterNot { room -> room.endpointId == endpointId }
				.toTypedArray()

		mutateState { state.copy(rooms = rooms) }
	}
}

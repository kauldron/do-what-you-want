package com.lockwood.room.feature.discover

import com.lockwood.connections.model.EndpointId
import com.lockwood.replicant.screen.Screen
import com.lockwood.room.model.Room

internal sealed class RoomsDiscoveryAction {

	object Idle : RoomsDiscoveryAction()
	object DiscoverRooms : RoomsDiscoveryAction()
	class NavigateToScreen(@JvmField val screen: Screen) : RoomsDiscoveryAction()
	class ShowMessage(@JvmField val message: String) : RoomsDiscoveryAction()

	class AcceptConnection(@JvmField val room: Room) : RoomsDiscoveryAction()
	class RejectConnection(@JvmField val room: Room) : RoomsDiscoveryAction()
	class RequestConnection(@JvmField val room: Room) : RoomsDiscoveryAction()

	class AddRooms(@JvmField val rooms: List<Room>) : RoomsDiscoveryAction()
	class AddRoom(@JvmField val room: Room) : RoomsDiscoveryAction()
	class RemoveRoom(@JvmField val endpointId: EndpointId) : RoomsDiscoveryAction()
}
package com.lockwood.room.data.preference

import com.lockwood.automata.core.EMPTY
import com.lockwood.connections.model.EndpointId
import com.lockwood.replicant.cache.preference.IPreferenceCacheManager
import com.lockwood.replicant.delegate.delegate
import com.lockwood.room.model.Room

internal class RoomPreferenceCacheManager(
		@JvmField
		private val preferenceCacheManager: IPreferenceCacheManager,
) : IRoomPreferenceCacheManager {

	private companion object {

		private const val CONNECTED_ROOM_ENDPOINT_ID = "connectedRoomEndpointId"
		private const val CONNECTED_ROOM_NAME = "connectedRoomName"

		private const val BROADCASTING_ROOM_NAME = "broadcastingRoomName"

		private const val IS_BROADCASTING = "isBroadcasting"
		private const val IS_CONNECTED = "isConnected"
	}

	override var connectedRoom: Room
		get() = preferenceCacheManager.getRoom()
		set(value) = preferenceCacheManager.putRoom(value)

	override var sharingRoomName: String by preferenceCacheManager.appPreference.delegate(
			name = BROADCASTING_ROOM_NAME,
			default = { String.EMPTY }
	)

	override var isConnected: Boolean by preferenceCacheManager.appPreference.delegate(
			name = IS_CONNECTED,
			default = { false }
	)

	override var isBroadcasting: Boolean by preferenceCacheManager.appPreference.delegate(
			name = IS_BROADCASTING,
			default = { false }
	)

	private fun IPreferenceCacheManager.getRoom(): Room {
		val endpointId = get(CONNECTED_ROOM_ENDPOINT_ID, Room.EMPTY.endpointId.toString())
		val name = get(CONNECTED_ROOM_NAME, Room.EMPTY.name)

		return Room(endpointId = EndpointId(endpointId), name = name)
	}

	private fun IPreferenceCacheManager.putRoom(room: Room) = with(room) {
		put(CONNECTED_ROOM_ENDPOINT_ID, endpointId.toString())
		put(CONNECTED_ROOM_NAME, name)
	}

}

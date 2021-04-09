package com.lockwood.room.data.preference

import com.lockwood.room.model.Room

interface IRoomPreferenceCacheManager {

	var connectedRoom: Room

	var sharingRoomName: String

	var isConnected: Boolean

	var isBroadcasting: Boolean
}
package com.lockwood.room.data.repostiory

import androidx.annotation.WorkerThread
import com.lockwood.connections.INearbyConnectionsManager

interface IRoomsRepository : INearbyConnectionsManager {

	@WorkerThread
	fun sendPayload(byteArray: ByteArray)

}

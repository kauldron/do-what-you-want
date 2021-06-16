package com.lockwood.room.data.repostiory

import com.lockwood.connections.INearbyConnectionsManager

internal class RoomsRepository(
    private val nearbyConnectionsManager: INearbyConnectionsManager,
) :
    IRoomsRepository,
    INearbyConnectionsManager by nearbyConnectionsManager {

    override var isBroadcasting: Boolean = false

    override var isConnected: Boolean = false

}

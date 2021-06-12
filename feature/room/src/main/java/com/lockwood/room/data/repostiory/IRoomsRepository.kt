package com.lockwood.room.data.repostiory

import com.lockwood.connections.INearbyConnectionsManager

interface IRoomsRepository : INearbyConnectionsManager {

    var isBroadcasting: Boolean

    var isConnected: Boolean

}
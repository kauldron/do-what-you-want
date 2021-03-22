package com.lockwood.room.data.repostiory

import com.lockwood.connections.NearbyConnectionsManager

internal class RoomsRepository(private val nearbyConnectionsManager: NearbyConnectionsManager) :
  IRoomsRepository, NearbyConnectionsManager by nearbyConnectionsManager

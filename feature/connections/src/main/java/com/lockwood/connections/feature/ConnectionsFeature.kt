package com.lockwood.connections.feature

import com.lockwood.automata.core.notSafeLazy
import com.lockwood.connections.NearbyConnectionsManager
import com.lockwood.connections.NearbyConnectionsManagerImpl
import com.lockwood.replicant.context.ApplicationContextProvider
import com.lockwood.replicant.feature.Feature

class ConnectionsFeature(
  private val contextProvider: ApplicationContextProvider,
) : Feature {

  val nearbyConnectionsManager: NearbyConnectionsManager by notSafeLazy {
    NearbyConnectionsManagerImpl(contextProvider.applicationContext)
  }
}

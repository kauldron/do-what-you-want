package com.lockwood.connections.feature

import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.ConnectionsClient
import com.lockwood.automata.core.notSafeLazy
import com.lockwood.connections.INearbyConnectionsManager
import com.lockwood.connections.NearbyConnectionsManager
import com.lockwood.connections.advertising.AdvertisingConnectionsManager
import com.lockwood.connections.advertising.IAdvertisingConnectionsManager
import com.lockwood.connections.discovery.DiscoveryConnectionsManager
import com.lockwood.connections.discovery.IDiscoveryConnectionsManager
import com.lockwood.replicant.context.ApplicationContextProvider
import com.lockwood.replicant.feature.Feature

class ConnectionsFeature(
    private val contextProvider: ApplicationContextProvider,
) : Feature {

    private val client: ConnectionsClient by notSafeLazy {
        Nearby.getConnectionsClient(contextProvider.applicationContext.value)
    }

    private val advertisingConnectionsManager: IAdvertisingConnectionsManager by notSafeLazy {
        AdvertisingConnectionsManager(client)
    }

    private val discoveryConnectionsManager: IDiscoveryConnectionsManager by notSafeLazy {
        DiscoveryConnectionsManager(client)
    }

    val nearbyConnectionsManager: INearbyConnectionsManager by notSafeLazy {
        NearbyConnectionsManager(client, advertisingConnectionsManager, discoveryConnectionsManager)
    }

}

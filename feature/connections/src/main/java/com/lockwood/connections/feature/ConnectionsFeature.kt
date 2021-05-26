package com.lockwood.connections.feature

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
		@JvmField
		private val contextProvider: ApplicationContextProvider,
) : Feature {

	val nearbyConnectionsManager: INearbyConnectionsManager by notSafeLazy {
		NearbyConnectionsManager(contextProvider.applicationContext, advertisingConnectionsManager, discoveryConnectionsManager)
	}

	private val advertisingConnectionsManager: IAdvertisingConnectionsManager by notSafeLazy {
		AdvertisingConnectionsManager(contextProvider.applicationContext)
	}

	private val discoveryConnectionsManager: IDiscoveryConnectionsManager by notSafeLazy {
		DiscoveryConnectionsManager(contextProvider.applicationContext)
	}

}

package com.lockwood.connections.discovery

import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.ConnectionsClient
import com.google.android.gms.nearby.connection.DiscoveryOptions
import com.google.android.gms.tasks.Task
import com.lockwood.automata.android.ApplicationContext
import com.lockwood.connections.NearbyConnectionsManager
import com.lockwood.connections.callback.DiscoveryCallback
import com.lockwood.connections.callback.adapter.DiscoveryCallbackAdapter

class DiscoveryConnectionsManager(
		@JvmField
		private val application: ApplicationContext,
) : IDiscoveryConnectionsManager {

	private val discoveryCallback = DiscoveryCallbackAdapter()

	private val client: ConnectionsClient
		get() = Nearby.getConnectionsClient(application.value)

	override fun startDiscovery(): Task<Void> {
		val options = DiscoveryOptions.Builder().setStrategy(NearbyConnectionsManager.CONNECTION_STRATEGY).build()
		return client.startDiscovery(NearbyConnectionsManager.SERVICE_ID, discoveryCallback, options)
	}

	override fun stopDiscovery() {
		client.stopDiscovery()
	}

	override fun addDiscoveryCallback(callback: DiscoveryCallback) {
		discoveryCallback.addListener(callback)
	}

	override fun removeDiscoveryCallback(callback: DiscoveryCallback) {
		discoveryCallback.removeListener(callback)
	}
}
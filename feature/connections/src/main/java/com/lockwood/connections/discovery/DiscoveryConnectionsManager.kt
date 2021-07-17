package com.lockwood.connections.discovery

import com.google.android.gms.nearby.connection.ConnectionsClient
import com.google.android.gms.nearby.connection.DiscoveryOptions
import com.lockwood.connections.NearbyConnectionsManager
import com.lockwood.connections.callback.DiscoveryCallback
import com.lockwood.connections.callback.adapter.DiscoveryCallbackAdapter

class DiscoveryConnectionsManager(
    private val client: ConnectionsClient,
) : IDiscoveryConnectionsManager {

    private val discoveryCallback = DiscoveryCallbackAdapter()

    override fun startDiscovery() {
        val options = DiscoveryOptions.Builder()
            .setStrategy(NearbyConnectionsManager.CONNECTION_STRATEGY)
            .build()

        client.startDiscovery(
            NearbyConnectionsManager.SERVICE_ID,
            discoveryCallback,
            options
        )
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
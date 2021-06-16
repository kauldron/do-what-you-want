package com.lockwood.connections.advertising

import com.google.android.gms.nearby.connection.AdvertisingOptions
import com.google.android.gms.nearby.connection.ConnectionsClient
import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.tasks.Task
import com.lockwood.connections.NearbyConnectionsManager
import com.lockwood.connections.callback.ConnectionCallback
import com.lockwood.connections.callback.adapter.ConnectionCallbackAdapter
import com.lockwood.connections.model.ConnectionStatus
import com.lockwood.connections.model.EndpointId

class AdvertisingConnectionsManager(
    private val client: ConnectionsClient,
) : IAdvertisingConnectionsManager {

    private val connectedEndpoints = mutableListOf<EndpointId>()

    private val lifecycleCallback = ConnectionCallbackAdapter()

    private val connectedEndpointsCallback = object : ConnectionCallback {
        override fun onConnectionResult(
            endpointId: EndpointId,
            connectionStatus: ConnectionStatus
        ) {
            if (connectionStatus is ConnectionStatus.Success) {
                connectedEndpoints.add(endpointId)
            }
        }

        override fun onDisconnected(endpointId: EndpointId) {
            connectedEndpoints.remove(endpointId)
        }
    }

    override fun startAdvertising(name: String): Task<Void> {
        addConnectionCallback(connectedEndpointsCallback)

        val options =
            AdvertisingOptions.Builder().setStrategy(NearbyConnectionsManager.CONNECTION_STRATEGY)
                .build()
        return client.startAdvertising(
            name,
            NearbyConnectionsManager.SERVICE_ID,
            lifecycleCallback,
            options
        )
    }

    override fun sendPayload(byteArray: ByteArray) {
        if (connectedEndpoints.isNotEmpty()) {
            val streamPayload = Payload.fromBytes(byteArray)
            val endpoints = connectedEndpoints.map(EndpointId::toString)

            client.sendPayload(endpoints, streamPayload)
        }
    }

    override fun requestConnection(name: String, endpointId: EndpointId): Task<Void> {
        return client.requestConnection(name, endpointId.toString(), lifecycleCallback)
    }

    override fun stopAdvertising() {
        client.stopAdvertising()
        connectedEndpoints.clear()
    }

    override fun addConnectionCallback(callback: ConnectionCallback) {
        lifecycleCallback.addListener(callback)
    }

    override fun removeConnectionCallback(callback: ConnectionCallback) {
        lifecycleCallback.removeListener(callback)
    }

}
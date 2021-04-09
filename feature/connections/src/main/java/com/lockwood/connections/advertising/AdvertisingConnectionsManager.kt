package com.lockwood.connections.advertising

import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.AdvertisingOptions
import com.google.android.gms.nearby.connection.ConnectionsClient
import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.tasks.Task
import com.lockwood.automata.android.ApplicationContext
import com.lockwood.connections.NearbyConnectionsManager
import com.lockwood.connections.callback.ConnectionCallback
import com.lockwood.connections.callback.adapter.ConnectionCallbackAdapter
import com.lockwood.connections.model.ConnectionInfo
import com.lockwood.connections.model.ConnectionSuccess
import com.lockwood.connections.model.ConnectionsStatus
import com.lockwood.connections.model.EndpointId

class AdvertisingConnectionsManager(
		private val application: ApplicationContext,
) : IAdvertisingConnectionsManager {

	private val lifecycleCallback = ConnectionCallbackAdapter()

	private val connectedEndpoints = mutableListOf<EndpointId>()

	private val connectedEndpointsCallback = object : ConnectionCallback {

		override fun onConnectionInitiated(
				endpointId: EndpointId,
				connectionInfo: ConnectionInfo
		) = Unit

		override fun onConnectionResult(
				endpointId: EndpointId,
				connectionStatus: ConnectionsStatus
		) {
			if (connectionStatus is ConnectionSuccess) {
				connectedEndpoints.add(endpointId)
			}
		}

		override fun onDisconnected(endpointId: EndpointId) {
			connectedEndpoints.remove(endpointId)
		}
	}

	private val client: ConnectionsClient
		get() = Nearby.getConnectionsClient(application.value)

	override fun startAdvertising(name: String): Task<Void> {
		addConnectionCallback(connectedEndpointsCallback)

		val options = AdvertisingOptions.Builder().setStrategy(NearbyConnectionsManager.CONNECTION_STRATEGY).build()
		return client.startAdvertising(name, NearbyConnectionsManager.SERVICE_ID, lifecycleCallback, options)
	}

	override fun sendPayload(byteArray: ByteArray) {
		if (connectedEndpoints.isNotEmpty() && byteArray.isNotEmpty()) {
			val streamPayload: Payload = Payload.fromBytes(byteArray)
			val endpoints = connectedEndpoints.map(EndpointId::toString)
			client.sendPayload(endpoints, streamPayload)
		}
	}

	override fun requestConnection(name: String, endpointId: EndpointId): Task<Void> {
		return client.requestConnection(name, endpointId.toString(), lifecycleCallback)
	}

	override fun addConnectionCallback(callback: ConnectionCallback) {
		lifecycleCallback.addListener(callback)
	}

	override fun removeConnectionCallback(callback: ConnectionCallback) {
		lifecycleCallback.removeListener(callback)
	}

	override fun stopAdvertising() {
		connectedEndpoints.clear()
		client.stopAdvertising()
	}

}
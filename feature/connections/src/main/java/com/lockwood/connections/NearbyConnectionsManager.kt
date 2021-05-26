package com.lockwood.connections

import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.ConnectionsClient
import com.google.android.gms.nearby.connection.Strategy
import com.lockwood.automata.android.ApplicationContext
import com.lockwood.connections.advertising.IAdvertisingConnectionsManager
import com.lockwood.connections.callback.PayloadCallback
import com.lockwood.connections.callback.adapter.PayloadCallbackAdapter
import com.lockwood.connections.discovery.IDiscoveryConnectionsManager
import com.lockwood.connections.model.EndpointId

internal class NearbyConnectionsManager(
		@JvmField
		private val application: ApplicationContext,
		@JvmField
		private val advertisingManager: IAdvertisingConnectionsManager,
		@JvmField
		private val discoveryManager: IDiscoveryConnectionsManager,
) :
		INearbyConnectionsManager,
		IAdvertisingConnectionsManager by advertisingManager,
		IDiscoveryConnectionsManager by discoveryManager {

	internal companion object {

		internal const val SERVICE_ID = "com.lockwood.dwyw"

		internal val CONNECTION_STRATEGY = Strategy.P2P_STAR
	}

	private val payloadCallbackAdapter = PayloadCallbackAdapter()

	private val client: ConnectionsClient
		get() = Nearby.getConnectionsClient(application.value)

	override fun acceptConnection(endpointId: EndpointId) {
		client.acceptConnection(endpointId.toString(), payloadCallbackAdapter)
	}

	override fun rejectConnection(endpointId: EndpointId) {
		client.rejectConnection(endpointId.toString())
	}

	override fun disconnectFromEndpoint(endpointId: EndpointId) {
		client.disconnectFromEndpoint(endpointId.toString())
	}

	override fun addPayloadCallback(callback: PayloadCallback) {
		payloadCallbackAdapter.addListener(callback)
	}

	override fun removePayloadCallback(callback: PayloadCallback) {
		payloadCallbackAdapter.removeListener(callback)
	}

}

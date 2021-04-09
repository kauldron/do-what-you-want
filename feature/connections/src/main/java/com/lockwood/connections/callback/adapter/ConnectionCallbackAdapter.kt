package com.lockwood.connections.callback.adapter

import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback
import com.google.android.gms.nearby.connection.ConnectionResolution
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes.STATUS_ERROR
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes.STATUS_OK
import com.lockwood.connections.callback.ConnectionCallback
import com.lockwood.connections.model.ConnectionError
import com.lockwood.connections.model.ConnectionInfo
import com.lockwood.connections.model.ConnectionRejected
import com.lockwood.connections.model.ConnectionSuccess
import com.lockwood.connections.model.ConnectionUnknownStatus
import com.lockwood.connections.model.ConnectionsStatus
import com.lockwood.connections.model.EndpointId

private typealias NearbyConnectionInfo = com.google.android.gms.nearby.connection.ConnectionInfo

internal class ConnectionCallbackAdapter :
		ConnectionLifecycleCallback(), CallbackAdapter<ConnectionCallback> {

	// TODO: Add OneWayMapper<NearbyConnectionInfo, ConnectionInfo>
	// TODO: Add OneWayMapper<NearbyConnectionResolution, ConnectionInfo>
	private val listeners: MutableList<ConnectionCallback> = mutableListOf()

	override fun onConnectionInitiated(id: String, info: NearbyConnectionInfo) {
		val endpointId = EndpointId(id)
		val connectionInfo = ConnectionInfo(info.isIncomingConnection, info.authenticationToken, info.endpointName)

		listeners.forEach { it.onConnectionInitiated(endpointId, connectionInfo) }
	}

	override fun onConnectionResult(id: String, resolution: ConnectionResolution) {
		val endpointId = EndpointId(id)
		val connectionStatus: ConnectionsStatus = when (resolution.status.statusCode) {
			STATUS_OK -> ConnectionSuccess(resolution.status)
			STATUS_CONNECTION_REJECTED -> ConnectionRejected(resolution.status)
			STATUS_ERROR -> ConnectionError(resolution.status)
			else -> ConnectionUnknownStatus(resolution.status)
		}

		listeners.forEach { it.onConnectionResult(endpointId, connectionStatus) }
	}

	override fun onDisconnected(id: String) {
		val endpointId = EndpointId(id)

		listeners.forEach { it.onDisconnected(endpointId) }
	}

	override fun addListener(callback: ConnectionCallback) {
		if (!listeners.contains(callback)) {
			listeners.add(callback)
		}
	}

	override fun removeListener(callback: ConnectionCallback) {
		listeners.remove(callback)
	}
}

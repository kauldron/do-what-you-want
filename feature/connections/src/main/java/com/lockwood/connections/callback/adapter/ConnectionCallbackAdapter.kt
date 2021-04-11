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
import com.lockwood.connections.model.NearbyConnectionInfo

internal class ConnectionCallbackAdapter :
		ConnectionLifecycleCallback(), CallbackAdapter<ConnectionCallback> {

	private val listeners: MutableList<ConnectionCallback> = mutableListOf()

	override fun onConnectionInitiated(id: String, info: NearbyConnectionInfo) {
		val endpointId = EndpointId(id)
		val connectionInfo = ConnectionInfo(info.endpointName)

		listeners.forEach { it.onConnectionInitiated(endpointId, connectionInfo) }
	}

	override fun onConnectionResult(id: String, resolution: ConnectionResolution) = with(resolution.status) {
		val endpointId = EndpointId(id)
		val connectionStatus: ConnectionsStatus = when (statusCode) {
			STATUS_OK -> ConnectionSuccess(this)
			STATUS_CONNECTION_REJECTED -> ConnectionRejected(this)
			STATUS_ERROR -> ConnectionError(this)
			else -> ConnectionUnknownStatus(this)
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

package com.lockwood.connections.callback.adapter

import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback
import com.google.android.gms.nearby.connection.ConnectionResolution
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes.*
import com.lockwood.connections.callback.ConnectionCallback
import com.lockwood.connections.model.ConnectionInfo
import com.lockwood.connections.model.ConnectionStatus
import com.lockwood.connections.model.ConnectionStatus.*
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

    override fun onConnectionResult(id: String, resolution: ConnectionResolution) =
        with(resolution.status) {
            val endpointId = EndpointId(id)
            val connectionStatus: ConnectionStatus = when (statusCode) {
                STATUS_OK -> Success(this)
                STATUS_CONNECTION_REJECTED -> Rejected(this)
                STATUS_ERROR -> Error(this)
                else -> Unknown(this)
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

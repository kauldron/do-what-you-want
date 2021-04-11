package com.lockwood.connections.callback

import com.lockwood.connections.model.ConnectionInfo
import com.lockwood.connections.model.ConnectionsStatus
import com.lockwood.connections.model.EndpointId

interface ConnectionCallback {

	fun onConnectionInitiated(endpointId: EndpointId, connectionInfo: ConnectionInfo) = Unit

	fun onConnectionResult(endpointId: EndpointId, connectionStatus: ConnectionsStatus) = Unit

	fun onDisconnected(endpointId: EndpointId) = Unit
}

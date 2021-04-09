package com.lockwood.connections

import com.lockwood.connections.advertising.IAdvertisingConnectionsManager
import com.lockwood.connections.callback.PayloadCallback
import com.lockwood.connections.discovery.IDiscoveryConnectionsManager
import com.lockwood.connections.model.EndpointId

interface INearbyConnectionsManager : IAdvertisingConnectionsManager, IDiscoveryConnectionsManager {

	fun acceptConnection(endpointId: EndpointId)

	fun rejectConnection(endpointId: EndpointId)

	fun disconnectFromEndpoint(endpointId: EndpointId)

	fun addPayloadCallback(callback: PayloadCallback)

	fun removePayloadCallback(callback: PayloadCallback)

}
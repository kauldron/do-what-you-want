package com.lockwood.connections.callback

import com.lockwood.connections.model.EndpointId
import com.lockwood.connections.model.EndpointInfo

interface DiscoveryCallback {

	fun onEndpointFound(endpointId: EndpointId, info: EndpointInfo) = Unit

	fun onEndpointLost(endpointId: EndpointId) = Unit
}

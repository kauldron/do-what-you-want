package com.lockwood.connections.callback.adapter

import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback
import com.lockwood.connections.callback.DiscoveryCallback
import com.lockwood.connections.model.EndpointId
import com.lockwood.connections.model.EndpointInfo

internal class DiscoveryCallbackAdapter :
    EndpointDiscoveryCallback(), CallbackAdapter<DiscoveryCallback> {

    private val listeners: MutableList<DiscoveryCallback> = mutableListOf()

    override fun onEndpointFound(id: String, info: DiscoveredEndpointInfo) {
        val endpointId = EndpointId(id)
        val endpointInfo = EndpointInfo(info.serviceId, info.endpointName)

        listeners.forEach { it.onEndpointFound(endpointId, endpointInfo) }
    }

    override fun onEndpointLost(id: String) {
        val endpointId = EndpointId(id)

        listeners.forEach { it.onEndpointLost(endpointId) }
    }

    override fun addListener(callback: DiscoveryCallback) {
        listeners.add(callback)
    }

    override fun removeListener(callback: DiscoveryCallback) {
        listeners.remove(callback)
    }
}

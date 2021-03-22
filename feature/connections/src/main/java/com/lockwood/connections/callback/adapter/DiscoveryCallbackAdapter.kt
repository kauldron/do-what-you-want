package com.lockwood.connections.callback.adapter

import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback
import com.lockwood.connections.callback.DiscoveryCallback
import timber.log.Timber

internal class DiscoveryCallbackAdapter :
  EndpointDiscoveryCallback(), CallbackAdapter<DiscoveryCallback> {

  private val listeners: MutableList<DiscoveryCallback> = mutableListOf()

  override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
    Timber.d(
      "onEndpointFound = endpointId: $endpointId; info: ${info.endpointName}, ${info.serviceId}"
    )
    listeners.forEach(DiscoveryCallback::onEndpointFound)
  }

  override fun onEndpointLost(endpointId: String) {
    Timber.d("onEndpointLost = endpointId: $endpointId")
    listeners.forEach(DiscoveryCallback::onEndpointLost)
  }

  override fun addListener(callback: DiscoveryCallback) {
    listeners.add(callback)
  }
}

package com.lockwood.connections.callback.adapter

import com.google.android.gms.nearby.connection.ConnectionInfo
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback
import com.google.android.gms.nearby.connection.ConnectionResolution
import com.lockwood.connections.callback.ConnectionCallback
import timber.log.Timber

internal class ConnectionCallbackAdapter :
  ConnectionLifecycleCallback(), CallbackAdapter<ConnectionCallback> {

  private val listeners: MutableList<ConnectionCallback> = mutableListOf()

  override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
    Timber.d("onConnectionInitiated = endpointId: $endpointId; connectionInfo: $connectionInfo.")
    listeners.forEach(ConnectionCallback::onConnectionInitiated)
  }

  override fun onConnectionResult(endpointId: String, resolution: ConnectionResolution) {
    Timber.d("onConnectionResult = endpointId: $endpointId; resolution: ${resolution.status}")
    listeners.forEach(ConnectionCallback::onConnectionResult)
  }

  override fun onDisconnected(endpointId: String) {
    Timber.d("onDisconnected = endpointId: $endpointId")
    listeners.forEach(ConnectionCallback::onDisconnected)
  }

  override fun addListener(callback: ConnectionCallback) {
    listeners.add(callback)
  }
}

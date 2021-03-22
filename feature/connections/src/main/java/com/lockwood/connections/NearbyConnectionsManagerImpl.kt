package com.lockwood.connections

import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.AdvertisingOptions
import com.google.android.gms.nearby.connection.ConnectionsClient
import com.google.android.gms.nearby.connection.DiscoveryOptions
import com.google.android.gms.nearby.connection.Strategy
import com.google.android.gms.tasks.Task
import com.lockwood.automata.android.ApplicationContext
import com.lockwood.automata.core.notSafeLazy
import com.lockwood.connections.callback.ConnectionCallback
import com.lockwood.connections.callback.DiscoveryCallback
import com.lockwood.connections.callback.adapter.ConnectionCallbackAdapter
import com.lockwood.connections.callback.adapter.DiscoveryCallbackAdapter

internal class NearbyConnectionsManagerImpl(
  private val application: ApplicationContext,
) : NearbyConnectionsManager {

  private companion object {

    private const val SERVICE_ID = "com.lockwood.dwyw"

    private val CONNECTION_STRATEGY = Strategy.P2P_STAR
  }

  private val lifecycleCallback = ConnectionCallbackAdapter()

  private val discoveryCallback = DiscoveryCallbackAdapter()

  private val client: ConnectionsClient by notSafeLazy {
    Nearby.getConnectionsClient(application.value)
  }

  override fun startAdvertising(name: String): Task<Void> {
    val options = AdvertisingOptions.Builder().setStrategy(CONNECTION_STRATEGY).build()
    return client.startAdvertising(name, SERVICE_ID, lifecycleCallback, options)
  }

  override fun startDiscovery(): Task<Void> {
    val options = DiscoveryOptions.Builder().setStrategy(CONNECTION_STRATEGY).build()
    return client.startDiscovery(SERVICE_ID, discoveryCallback, options)
  }

  override fun addConnectionCallback(callback: ConnectionCallback) {
    lifecycleCallback.addListener(callback)
  }

  override fun addDiscoveryCallback(callback: DiscoveryCallback) {
    discoveryCallback.addListener(callback)
  }

  override fun stopAdvertising() {
    client.stopAdvertising()
  }

  override fun stopDiscovery() {
    client.stopDiscovery()
  }
}

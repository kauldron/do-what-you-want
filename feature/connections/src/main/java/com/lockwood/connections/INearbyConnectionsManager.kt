package com.lockwood.connections

import com.google.android.gms.tasks.Task
import com.lockwood.connections.callback.ConnectionCallback
import com.lockwood.connections.callback.DiscoveryCallback

interface INearbyConnectionsManager {

  fun startAdvertising(name: String): Task<Void>

  fun startDiscovery(): Task<Void>

  fun addConnectionCallback(callback: ConnectionCallback)

  fun addDiscoveryCallback(callback: DiscoveryCallback)

  fun removeConnectionCallback(callback: ConnectionCallback)

  fun removeDiscoveryCallback(callback: DiscoveryCallback)

  fun stopAdvertising()

  fun stopDiscovery()
}

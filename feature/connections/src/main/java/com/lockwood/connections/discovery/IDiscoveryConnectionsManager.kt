package com.lockwood.connections.discovery

import com.google.android.gms.tasks.Task
import com.lockwood.connections.callback.DiscoveryCallback

interface IDiscoveryConnectionsManager {

    fun startDiscovery(): Task<Void>

    fun addDiscoveryCallback(callback: DiscoveryCallback)

    fun removeDiscoveryCallback(callback: DiscoveryCallback)

    fun stopDiscovery()

}
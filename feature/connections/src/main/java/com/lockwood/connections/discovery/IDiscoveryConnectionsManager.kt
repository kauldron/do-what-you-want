package com.lockwood.connections.discovery

import com.lockwood.connections.callback.DiscoveryCallback

interface IDiscoveryConnectionsManager {

    fun startDiscovery()

    fun addDiscoveryCallback(callback: DiscoveryCallback)

    fun removeDiscoveryCallback(callback: DiscoveryCallback)

    fun stopDiscovery()

}
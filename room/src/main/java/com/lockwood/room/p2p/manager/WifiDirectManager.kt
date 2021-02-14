package com.lockwood.room.p2p.manager

import android.content.Context
import android.net.wifi.p2p.WifiP2pManager

interface WifiDirectManager {

    val isDeviceSupported: Boolean

    val isWifiP2pSupported: Boolean

    @kotlin.jvm.Throws(IllegalStateException::class)
    fun init(): WifiP2pManager.Channel

    fun discoverPeers(listener: WifiP2pManager.ActionListener)
}

// TODO: move to separate class
interface ReceiverManager {

    fun registerReceiver(context: Context)

    fun unregisterReceiver(context: Context)
}
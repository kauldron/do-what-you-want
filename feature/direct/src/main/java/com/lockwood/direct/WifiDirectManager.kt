package com.lockwood.direct

import android.net.wifi.p2p.WifiP2pManager

interface WifiDirectManager {

    val isDeviceSupported: Boolean

    val isWifiP2pSupported: Boolean

    @kotlin.jvm.Throws(IllegalStateException::class)
    fun init(): WifiP2pManager.Channel

    fun discoverPeers(listener: WifiP2pManager.ActionListener)
}

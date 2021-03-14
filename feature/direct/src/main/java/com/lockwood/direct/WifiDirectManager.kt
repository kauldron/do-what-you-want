package com.lockwood.direct

import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.p2p.nsd.WifiP2pServiceRequest

interface WifiDirectManager {

    val isDeviceSupported: Boolean

    val isWifiP2pSupported: Boolean

    @kotlin.jvm.Throws(IllegalStateException::class)
    fun init(): WifiP2pManager.Channel

    fun discoverPeers(listener: WifiP2pActionListener)

    fun requestServices(serviceRequest: WifiP2pServiceRequest, listener: WifiP2pActionListener)

    fun discoverServices(listener: WifiP2pActionListener)
}

package com.lockwood.direct

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.WIFI_P2P_SERVICE
import android.content.Context.WIFI_SERVICE
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pManager
import com.lockwood.automata.core.notSafeLazy

// TODO: make internal
class WifiDirectManagerImpl(
        context: Context,
) : WifiDirectManager {

    private val appContext: Context = context.applicationContext

    private val wifiP2pManager by notSafeLazy {
        appContext.getSystemService(WIFI_P2P_SERVICE) as WifiP2pManager
    }
    private val channel by notSafeLazy {
        wifiP2pManager.initialize(context, context.mainLooper, null)
    }

    override val isDeviceSupported: Boolean
        get() = appContext.packageManager.hasSystemFeature(PackageManager.FEATURE_WIFI_DIRECT)

    override val isWifiP2pSupported: Boolean
        @SuppressLint("WifiManagerPotentialLeak")
        get() = (appContext.getSystemService(WIFI_SERVICE) as WifiManager).isP2pSupported

    override fun init(): WifiP2pManager.Channel {
        if (!isDeviceSupported) {
            error("Wi-Fi Direct is not supported by this device")
        }

        if (!isWifiP2pSupported) {
            error("Wi-Fi Direct is not supported by the hardware or Wi-Fi is off")
        }

        // registers the application with the Wi-Fi framework
        return channel
    }

    @SuppressLint("MissingPermission")
    override fun discoverPeers(listener: WifiP2pManager.ActionListener) {
        wifiP2pManager.discoverPeers(channel, listener)
    }

}
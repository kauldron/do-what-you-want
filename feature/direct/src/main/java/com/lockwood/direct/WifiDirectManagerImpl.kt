package com.lockwood.direct

import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pManager
import com.lockwood.automata.android.ApplicationContext
import com.lockwood.automata.android.getSystemService
import com.lockwood.automata.core.notSafeLazy

// TODO: make internal
internal class WifiDirectManagerImpl(
    context: ApplicationContext,
) : WifiDirectManager {

    private val application: Application = context.application

    private val wifiP2pManager: WifiP2pManager by notSafeLazy {
        application.getSystemService()
    }

    private val channel: WifiP2pManager.Channel by notSafeLazy {
        wifiP2pManager.initialize(application, application.mainLooper, null)
    }

    override val isDeviceSupported: Boolean
        get() = application.packageManager.hasSystemFeature(PackageManager.FEATURE_WIFI_DIRECT)

    override val isWifiP2pSupported: Boolean
        @SuppressLint("WifiManagerPotentialLeak")
        get() = application.getSystemService<WifiManager>().isP2pSupported

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
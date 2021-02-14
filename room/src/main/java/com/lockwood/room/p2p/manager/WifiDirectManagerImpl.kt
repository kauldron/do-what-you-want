package com.lockwood.room.p2p.manager

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.WIFI_P2P_SERVICE
import android.content.Context.WIFI_SERVICE
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pManager

// TODO: make internal
class WifiDirectManagerImpl(
    context: Context,
) : WifiDirectManager {

    private val appContext: Context = context.applicationContext

    private val wifiP2pManager by lazy(LazyThreadSafetyMode.NONE) {
        appContext.getSystemService(WIFI_P2P_SERVICE) as WifiP2pManager
    }
    private val channel by lazy(LazyThreadSafetyMode.NONE) {
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

// TODO: move to separate class
class WifiDirectReceiverManager(wifiDirectManager: WifiDirectManager) : ReceiverManager {

    private val receiver by lazy(LazyThreadSafetyMode.NONE) {
        WiFiDirectBroadcastReceiver(wifiDirectManager)
    }

    override fun registerReceiver(context: Context) {
        val intentFilter = IntentFilter().apply {
            addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
        }
        context.registerReceiver(receiver, intentFilter)
    }

    override fun unregisterReceiver(context: Context) {
        context.unregisterReceiver(receiver)
    }

}
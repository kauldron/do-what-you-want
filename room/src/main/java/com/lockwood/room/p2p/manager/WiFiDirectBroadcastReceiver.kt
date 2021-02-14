package com.lockwood.room.p2p.manager

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.p2p.WifiP2pManager
import android.util.Log

class WiFiDirectBroadcastReceiver(
    private val directManager: WifiDirectManager,
) : BroadcastReceiver() {

    private val Intent.isWifiDirectEnabled: Boolean
        get() {
            val state = getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
            return state == WifiP2pManager.WIFI_P2P_STATE_ENABLED
        }

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
                if (intent.isWifiDirectEnabled) {
                    Log.d("WiFiDirectBroadcast", "Wifi P2P is enabled")
                } else {
                    Log.d("WiFiDirectBroadcast", "Wi-Fi P2P is not enabled")
                }
            }
            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
//                manager.requestPeers(channel) {}
            }
            else -> {
                // do nothing
            }
        }
    }

}

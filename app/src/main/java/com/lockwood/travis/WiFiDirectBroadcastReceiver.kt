package com.lockwood.travis

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.p2p.WifiP2pManager
import android.util.Log

class WiFiDirectBroadcastReceiver(
        private val manager: WifiP2pManager,
        private val channel: WifiP2pManager.Channel,
        // TODO: Replace with WiFiDirect callback
        private val activity: MainActivity
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
            else -> {
                // do nothing
            }
        }
    }

}

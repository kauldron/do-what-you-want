package com.lockwood.direct.reciver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.p2p.WifiP2pManager
import com.lockwood.direct.WifiDirectManager
import timber.log.Timber

internal class WiFiDirectBroadcastReceiver(
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
                    Timber.d("Wifi P2P is enabled")
                } else {
                    Timber.d("Wi-Fi P2P is not enabled")
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

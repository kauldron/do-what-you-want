package com.lockwood.direct.reciver

import android.content.Context
import android.content.IntentFilter
import android.net.wifi.p2p.WifiP2pManager
import com.lockwood.core.ReceiverManager
import com.lockwood.direct.WifiDirectManager

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
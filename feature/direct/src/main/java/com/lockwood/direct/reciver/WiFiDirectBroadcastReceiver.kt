package com.lockwood.direct.reciver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.p2p.WifiP2pManager.EXTRA_P2P_DEVICE_LIST
import timber.log.Timber

internal class WiFiDirectBroadcastReceiver : BroadcastReceiver() {

  private val Intent.isWifiDirectEnabled: Boolean
    get() {
      val state = getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
      return state == WifiP2pManager.WIFI_P2P_STATE_ENABLED
    }

  @SuppressLint("MissingPermission")
  override fun onReceive(context: Context, intent: Intent) {
    when (intent.action) {
      WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
        Timber.d("Wifi P2P is enabled: ${intent.isWifiDirectEnabled}")
      }
      WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
        Timber.d("Wifi P2P peer list has changed: ${intent.getParcelableExtra<WifiP2pDeviceList>(EXTRA_P2P_DEVICE_LIST)}")
      }
      WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
        Timber.d("Wifi P2P connection state changed: $intent")
      }
      WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION -> {
        Timber.d("Wifi P2P discovery changed: $intent")
      }
      WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {
        Timber.d("Wifi P2P this device changed: $intent")
      }
      else -> Unit
    }
  }
}

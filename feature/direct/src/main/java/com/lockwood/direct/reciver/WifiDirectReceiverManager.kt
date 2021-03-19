package com.lockwood.direct.reciver

import android.content.Context
import android.content.IntentFilter
import android.net.wifi.p2p.WifiP2pManager
import com.lockwood.automata.core.notSafeLazy
import com.lockwood.replicant.receiver.ReceiverManager

internal class WifiDirectReceiverManager : ReceiverManager {

	private val receiver by notSafeLazy { WiFiDirectBroadcastReceiver() }

	override fun registerReceiver(context: Context) {
		val intentFilter = IntentFilter().apply {
			addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
			addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
			addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
			addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
		}
		context.applicationContext.registerReceiver(receiver, intentFilter)
	}

	override fun unregisterReceiver(context: Context) {
		context.applicationContext.unregisterReceiver(receiver)
	}

}
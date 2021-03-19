package com.lockwood.direct

import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.p2p.WifiP2pManager.ActionListener
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo
import android.net.wifi.p2p.nsd.WifiP2pServiceRequest
import com.lockwood.automata.android.ApplicationContext
import com.lockwood.automata.android.getSystemService
import com.lockwood.automata.core.notSafeLazy
import com.lockwood.direct.utils.WifiP2pManagerUtils
import java.net.ServerSocket

internal class WifiDirectManagerImpl(
		context: ApplicationContext,
) : WifiDirectManager {

	private val application: Application = context.application

	private val channel: WifiP2pManager.Channel by notSafeLazy {
		wifiP2pManager.initialize(application, application.mainLooper, null)
	}

	private val availablePort: Int by notSafeLazy {
		ServerSocket(0).localPort
	}

	private val wifiP2pManager: WifiP2pManager
		get() = application.getSystemService()

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
	override fun discoverPeers(listener: WifiP2pActionListener) {
		wrapWithActionListener(listener) {
			wifiP2pManager.discoverPeers(channel, it)
		}
	}

	override fun requestServices(serviceRequest: WifiP2pServiceRequest, listener: WifiP2pActionListener) {
		wrapWithActionListener(listener) {
			wifiP2pManager.addServiceRequest(channel, serviceRequest, it)
		}
	}

	@SuppressLint("MissingPermission")
	override fun addLocalService(serviceInfo: WifiP2pDnsSdServiceInfo, listener: WifiP2pActionListener) {
		wrapWithActionListener(listener) {
			wifiP2pManager.addLocalService(channel, serviceInfo, it)
		}
	}

	@SuppressLint("MissingPermission")
	override fun discoverServices(listener: WifiP2pActionListener) {
		wrapWithActionListener(listener) {
			wifiP2pManager.discoverServices(channel, it)
		}
	}

	private inline fun wrapWithActionListener(
			listener: WifiP2pActionListener,
			action: (ActionListener) -> Unit,
	) {
		val actionListener: ActionListener = WifiP2pManagerUtils.wrapWithActionListener(listener)
		action(actionListener)
	}

}
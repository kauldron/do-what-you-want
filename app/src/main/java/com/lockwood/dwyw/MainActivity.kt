package com.lockwood.dwyw

import android.Manifest
import android.annotation.SuppressLint
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.p2p.WifiP2pManager.ChannelListener
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), ActivityResultCallback<Boolean>, ChannelListener, WifiP2pManager.PeerListListener {

	private val isSupportWifiDirect: Boolean
		get() = packageManager.hasSystemFeature(PackageManager.FEATURE_WIFI_DIRECT)

	private val isP2pSupported: Boolean
		get() = (applicationContext.getSystemService(WIFI_SERVICE) as WifiManager).isP2pSupported

	// TODO: add getSystemService ktx
	// TODO: move to p2p package
	private val wifiP2pManager by lazy(LazyThreadSafetyMode.NONE) {
		getSystemService(WIFI_P2P_SERVICE) as WifiP2pManager
	}
	private val retryChannel by lazy(LazyThreadSafetyMode.NONE) {
		wifiP2pManager.initialize(this, mainLooper, this)
	}
	private val receiver by lazy(LazyThreadSafetyMode.NONE) {
		WiFiDirectBroadcastReceiver(wifiP2pManager, retryChannel, this)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		requestPermissions()
	}

	override fun onResume() {
		super.onResume()
		registerWiFiDirectReceiver()
	}

	override fun onPause() {
		super.onPause()
		unregisterWiFiDirectReceiver()
	}

	override fun onActivityResult(isGranted: Boolean) {
		// TODO: add Timber for logging
		Log.d(TAG, "onActivityResult: $isGranted")

		if (isGranted) {
			if (initP2p()) {
				// TODO: add and Use Toaster
				discoverPeers()
			} else {
				showToast("P2p is not supported")
			}
		}
	}

	override fun onChannelDisconnected() {
	}

	override fun onPeersAvailable(peers: WifiP2pDeviceList) {
		showToast(peers.toString())
	}

	private fun requestPermissions() {
		val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission(), this)
		requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
	}

	private fun initP2p(): Boolean {
		if (!isSupportWifiDirect) {
			return false
		}

		if (!isP2pSupported) {
			return false
		}

		try {
			// init WifiP2pManager Channel
			retryChannel
		} catch (e: Exception) {
			Log.e(TAG, "Cannot initialize Wi-Fi Direct", e)
			return false
		}

		return true
	}

	private fun registerWiFiDirectReceiver() {
		val intentFilter = IntentFilter().apply {
			addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
			addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
			addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
			addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
		}
		registerReceiver(receiver, intentFilter)
	}

	private fun unregisterWiFiDirectReceiver() {
		unregisterReceiver(receiver)
	}

	@SuppressLint("MissingPermission")
	private fun discoverPeers() {
		wifiP2pManager.discoverPeers(retryChannel, object : WifiP2pManager.ActionListener {

            override fun onSuccess() {
                showToast("discoverPeers: onSuccess")
            }

            override fun onFailure(reasonCode: Int) {
                showToast("discoverPeers: onFailure with $reasonCode code")
            }
        })
	}

	private fun showToast(message: String) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show()
	}

	private companion object {

		val TAG = MainActivity::class.simpleName
	}

}

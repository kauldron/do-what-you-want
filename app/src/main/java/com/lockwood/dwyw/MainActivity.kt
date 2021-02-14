package com.lockwood.dwyw

import android.Manifest
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.p2p.WifiP2pManager.ChannelListener
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.lockwood.room.p2p.manager.ReceiverManager
import com.lockwood.room.p2p.manager.WifiDirectManager
import com.lockwood.room.p2p.manager.WifiDirectManagerImpl
import com.lockwood.room.p2p.manager.WifiDirectReceiverManager

class MainActivity : AppCompatActivity(), ActivityResultCallback<Boolean>, ChannelListener,
	WifiP2pManager.PeerListListener {

	// TODO: add getSystemService ktx
	// TODO: move to application feature
	private val wifiDirectManager: WifiDirectManager by lazy(LazyThreadSafetyMode.NONE) {
		WifiDirectManagerImpl(this)
	}
	private val wifiDirectReceiver: ReceiverManager by lazy(LazyThreadSafetyMode.NONE) {
		WifiDirectReceiverManager(wifiDirectManager)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		requestPermissions()
	}

	override fun onResume() {
		super.onResume()
		wifiDirectReceiver.registerReceiver(this)
	}

	override fun onPause() {
		super.onPause()
		wifiDirectReceiver.unregisterReceiver(this)
	}

	override fun onActivityResult(isGranted: Boolean) {
		// TODO: add Timber for logging
		Log.d(TAG, "onActivityResult: $isGranted")

		if (isGranted && initP2p()) {
			// TODO: add and Use Toaster
			discoverPeers()
		}
	}

	override fun onChannelDisconnected() {
	}

	override fun onPeersAvailable(peers: WifiP2pDeviceList) {
		showToast(peers.toString())
	}

	private fun requestPermissions() {
		val requestPermissionLauncher =
			registerForActivityResult(ActivityResultContracts.RequestPermission(), this)
		requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
	}

	private fun initP2p(): Boolean {
		try {
			wifiDirectManager.init()
		} catch (e: Exception) {
			showToast(e.message.toString())
			return false
		}

		return true
	}

	private fun discoverPeers() {
		wifiDirectManager.discoverPeers(object : WifiP2pManager.ActionListener {

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

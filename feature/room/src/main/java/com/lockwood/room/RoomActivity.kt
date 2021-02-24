package com.lockwood.room

import android.Manifest
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.lockwood.automata.core.notSafeLazy
import com.lockwood.direct.WifiDirectManager
import com.lockwood.direct.WifiDirectManagerImpl
import com.lockwood.direct.reciver.WifiDirectReceiverManager
import com.lockwood.replicant.receiver.ReceiverManager

class RoomActivity : AppCompatActivity(R.layout.fragment_container),
	ActivityResultCallback<Boolean>,
	WifiP2pManager.ChannelListener, WifiP2pManager.PeerListListener {

	// TODO: add getSystemService ktx
	// TODO: move to application feature
	private val wifiDirectManager: WifiDirectManager by notSafeLazy {
		WifiDirectManagerImpl(this)
	}
	private val wifiDirectReceiver: ReceiverManager by notSafeLazy {
		WifiDirectReceiverManager(wifiDirectManager)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		if (savedInstanceState == null) {
			// TODO: Show progress fragment
		}
		requestPermissions()
	}

	override fun onResume() {
		super.onResume()
		// TODO: Replace with receiver callbacks handler
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
		Log.d(TAG, "onPeersAvailable: ${peers.deviceList.size}}")
	}

	private fun requestPermissions() {
		val requestPermissionLauncher: ActivityResultLauncher<String> =
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
				Log.d(TAG, "discoverPeers: onSuccess")
			}

			override fun onFailure(reasonCode: Int) {
				// TODO: Show Stub Fragment
				showToast("discoverPeers: onFailure with $reasonCode code")
			}
		})
	}

	private fun showToast(message: String) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show()
	}

	private companion object {

		val TAG = RoomActivity::class.simpleName
	}

}


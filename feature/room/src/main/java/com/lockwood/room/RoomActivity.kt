package com.lockwood.room

import android.Manifest
import android.content.Intent
import android.net.wifi.p2p.nsd.WifiP2pServiceRequest
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.lockwood.automata.core.notSafeLazy
import com.lockwood.direct.WifiDirectManager
import com.lockwood.direct.WifiP2pActionListener
import com.lockwood.direct.WifiP2pError
import com.lockwood.direct.feature.DirectFeature
import com.lockwood.dwyw.core.screen.RoomScreen
import com.lockwood.dwyw.core.screen.RoomsScreen
import com.lockwood.dwyw.core.ui.BaseActivity
import com.lockwood.replicant.receiver.lifecycleAwareReceiver
import com.lockwood.replicant.screen.Screen
import com.lockwood.replicant.view.ProgressView
import com.lockwood.room.RoomActivity.ErrorScreen.DirectNotAvailable
import com.lockwood.room.RoomActivity.ErrorScreen.P2pError
import com.lockwood.room.feature.RoomsFeature
import com.lockwood.room.room.ui.RoomFragment
import com.lockwood.room.rooms.ui.RoomsFragment
import timber.log.Timber

class RoomActivity : BaseActivity(), ProgressView, ActivityResultCallback<Boolean> {

	init {
		lifecycleAwareReceiver { getFeature<DirectFeature>().wifiReceiverManager }
	}

	private val wifiDirectManager: WifiDirectManager by notSafeLazy {
		getFeature<DirectFeature>().wifiDirectManager
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(com.lockwood.replicant.R.layout.fragment_container_progress)

		if (savedInstanceState == null) {
			requestPermissions()
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		releaseFeature<RoomsFeature>()
	}

	override fun showScreen(screen: Screen) = when (screen) {
		is RoomsScreen -> showFragment(RoomsFragment.newInstance(), true)
		is RoomScreen -> showFragment(RoomFragment.newInstance(screen.id))
		else -> Unit
	}

	override fun showProgress() {
		findViewById<View>(com.lockwood.replicant.R.id.progress_bar).visibility = View.VISIBLE
	}

	override fun hideProgress() {
		findViewById<View>(com.lockwood.replicant.R.id.progress_bar).visibility = View.GONE
	}

	override fun onActivityResult(isGranted: Boolean) {
		Timber.d("onActivityResult: $isGranted")

		if (isGranted) {
			initP2p()
		}
	}

	private fun initP2p() = with(wifiDirectManager) {
		try {
			init()
			requestServices()
		} catch (e: IllegalStateException) {
			showErrorScreen(DirectNotAvailable)
		}
	}

	private fun requestServices() = with(wifiDirectManager) {
		requestServices(WifiP2pServiceRequest.newInstance(80), object : WifiP2pActionListener {
			override fun onSuccess() {
				Timber.d("Request services succeeded")
				discoverServices()
			}

			override fun onFailure(error: WifiP2pError) {
				Timber.d("Request services failed with reason $error")
				showErrorScreen(P2pError(error))
			}
		})
	}

	private fun discoverServices() = with(wifiDirectManager) {
		discoverServices(object : WifiP2pActionListener {
			override fun onSuccess() {
				Timber.d("Discover services succeeded")
				showToast("Discover services succeeded")
			}

			override fun onFailure(error: WifiP2pError) {
				Timber.d("Discover services failed with reason $error")
				showErrorScreen(P2pError(error))
			}
		})
	}

	private fun handleIntent(intent: Intent) {
		// TODO: Handle intent
	}

	private fun showErrorScreen(screen: ErrorScreen) = when (screen) {
		is DirectNotAvailable -> showToast("DirectNotAvailable")
		is P2pError -> showToast("P2pError: ${screen.error}")
	}

	private fun requestPermissions() {
		val requestPermissionLauncher: ActivityResultLauncher<String> =
				registerForActivityResult(ActivityResultContracts.RequestPermission(), this)
		requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
	}

	private fun showFragment(fragment: Fragment, fromBackStack: Boolean = false) {
		showFragment(com.lockwood.replicant.R.id.fragment_container, fragment, fromBackStack)
	}

	private fun showToast(string: String) {
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
	}

	private sealed class ErrorScreen {

		object DirectNotAvailable : ErrorScreen()

		class P2pError(val error: WifiP2pError) : ErrorScreen()
	}

}
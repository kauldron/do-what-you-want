package com.lockwood.room

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.lockwood.direct.feature.DirectFeature
import com.lockwood.dwyw.core.screen.RoomScreen
import com.lockwood.dwyw.core.screen.RoomsScreen
import com.lockwood.dwyw.core.ui.BaseStateActivity
import com.lockwood.replicant.event.observeEvents
import com.lockwood.replicant.ext.lazyViewModel
import com.lockwood.replicant.ext.observeState
import com.lockwood.replicant.receiver.lifecycleAwareReceiver
import com.lockwood.replicant.screen.ErrorScreen
import com.lockwood.replicant.screen.Screen
import com.lockwood.replicant.view.ErrorScreenView
import com.lockwood.replicant.view.MessageView
import com.lockwood.replicant.view.ProgressView
import com.lockwood.room.feature.RoomsFeature
import com.lockwood.room.room.ui.RoomFragment
import com.lockwood.room.rooms.ui.RoomsFragment
import com.lockwood.room.screen.DirectNotAvailableScreen
import com.lockwood.room.screen.P2pErrorScreen
import timber.log.Timber

internal class RoomDirectActivity :
  BaseStateActivity<RoomDirectViewState>(),
  MessageView,
  ProgressView,
  ErrorScreenView,
  ActivityResultCallback<Boolean> {

  private val viewModel: RoomDirectViewModel by lazyViewModel {
    RoomDirectViewModel(getFeature<DirectFeature>().wifiDirectManager)
  }

  init {
    lifecycleAwareReceiver { getFeature<DirectFeature>().wifiReceiverManager }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(com.lockwood.replicant.R.layout.fragment_container_progress)

    with(viewModel) {
      observeState(liveState, ::renderState)
      observeEvents(eventsQueue, ::onEvent)
    }

    if (savedInstanceState == null) {
      requestPermissions()
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    releaseFeature<RoomsFeature>()
  }

  override fun onActivityResult(isGranted: Boolean) {
    Timber.d("onActivityResult: $isGranted")

    if (isGranted) {
      viewModel.initP2p()
    }
  }

  override fun showScreen(screen: Screen) {
    return when (screen) {
      is RoomsScreen -> showFragment(RoomsFragment.newInstance(), true)
      is RoomScreen -> showFragment(RoomFragment.newInstance(screen.id))
      else -> Unit
    }
  }

  override fun showErrorScreen(screen: ErrorScreen) {
    return when (screen) {
      is DirectNotAvailableScreen -> showToast("DirectNotAvailable")
      is P2pErrorScreen -> showToast("P2pError: ${screen.error}")
      else -> Unit
    }
  }

  override fun renderState(viewState: RoomDirectViewState) {
    with(viewState) {
      renderDirectState(directState)
      renderLoading(isLoading)
    }
  }

  override fun showProgress() {
    findViewById<View>(com.lockwood.replicant.R.id.progress_bar).visibility = View.VISIBLE
  }

  override fun hideProgress() {
    findViewById<View>(com.lockwood.replicant.R.id.progress_bar).visibility = View.GONE
  }

  override fun showMessage(message: String) {
    showToast(message)
  }

  override fun showError(message: String) {
    showToast(message)
  }

  private fun renderDirectState(directState: DirectState) {
    Timber.d("Current state: $directState")
  }

  private fun handleIntent(intent: Intent) {
    // TODO: Handle intent
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
}

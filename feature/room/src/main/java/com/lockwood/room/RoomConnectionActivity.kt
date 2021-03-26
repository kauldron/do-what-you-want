package com.lockwood.room

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.lockwood.dwyw.core.screen.PermissionRequiredScreen
import com.lockwood.dwyw.core.ui.BaseActivity
import com.lockwood.replicant.screen.Screen
import com.lockwood.replicant.view.MessageView
import com.lockwood.replicant.view.ProgressView
import com.lockwood.room.client.ui.RoomsDiscoverFragment
import com.lockwood.room.feature.RoomsFeature
import com.lockwood.room.host.ui.RoomHostFragment
import com.lockwood.room.launcher.RoomArgs.Companion.IS_SHOW_ADVERTISING
import com.lockwood.room.launcher.RoomArgs.Companion.ROOM_ID
import com.lockwood.room.screen.RetryErrorScreen
import com.lockwood.room.screen.RoomConnectionScreen
import com.lockwood.room.screen.RoomsAdvertisingScreen
import com.lockwood.room.screen.RoomsDiscoveryScreen
import timber.log.Timber

internal class RoomConnectionActivity :
  BaseActivity(), MessageView, ProgressView, ActivityResultCallback<Boolean> {

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

  override fun onActivityResult(isGranted: Boolean) {
    Timber.d("onActivityResult: $isGranted")

    if (isGranted) {
      handleIntent()
    } else {
      showScreen(PermissionRequiredScreen)
    }
  }

  override fun showScreen(screen: Screen) {
    return when (screen) {
      is RoomsDiscoveryScreen -> {
        showFragmentViaBackStack(RoomsDiscoverFragment.newInstance())
      }
      is RoomsAdvertisingScreen -> {
        clearBackStack()
        showFragment<RoomHostFragment>(RoomHostFragment.newInstance())
      }
      is RoomConnectionScreen -> Unit
      is RetryErrorScreen -> Unit
      else -> super.showScreen(screen)
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

  private fun handleIntent() {
    with(intent.extras) {
      checkNotNull(this) {
        showScreen(RoomsDiscoveryScreen)
        return
      }

      when {
        containsKey(ROOM_ID) -> showScreen(RoomConnectionScreen(getInt(ROOM_ID)))
        getBoolean(IS_SHOW_ADVERTISING, false) -> showScreen(RoomsAdvertisingScreen)
        else -> showScreen(RoomsDiscoveryScreen)
      }
    }
  }

  private fun requestPermissions() {
    val requestPermissionLauncher: ActivityResultLauncher<String> =
      registerForActivityResult(ActivityResultContracts.RequestPermission(), this)
    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
  }

  private inline fun <reified T : Fragment> showFragment(
    fragment: Fragment,
  ) = showFragment<T>(com.lockwood.replicant.R.id.fragment_container, fragment)

  private fun showFragmentViaBackStack(
    fragment: Fragment,
  ) = showFragmentViaBackStack(com.lockwood.replicant.R.id.fragment_container, fragment)

  private fun showToast(string: String) {
    Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
  }
}

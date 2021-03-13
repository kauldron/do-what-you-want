package com.lockwood.room

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.lockwood.dwyw.core.screen.RoomScreen
import com.lockwood.dwyw.core.screen.RoomsScreen
import com.lockwood.dwyw.core.ui.BaseActivity
import com.lockwood.replicant.screen.Screen
import com.lockwood.replicant.view.ProgressView
import com.lockwood.room.feature.RoomsFeature
import com.lockwood.room.room.ui.RoomFragment
import com.lockwood.room.rooms.ui.RoomsFragment
import timber.log.Timber

class RoomActivity : BaseActivity(), ProgressView, ActivityResultCallback<Boolean> {

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
            // TODO: Handle intent
            showScreen(RoomsScreen)
        }
    }

    private fun requestPermissions() {
        val requestPermissionLauncher: ActivityResultLauncher<String> =
            registerForActivityResult(ActivityResultContracts.RequestPermission(), this)
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun showFragment(fragment: Fragment, fromBackStack: Boolean = false) {
        showFragment(com.lockwood.replicant.R.id.fragment_container, fragment, fromBackStack)
    }

}


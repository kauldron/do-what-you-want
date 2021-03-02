package com.lockwood.room.ui.activity

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.lockwood.automata.res.IdRes.Companion.toIdRes
import com.lockwood.dwyw.core.screen.RoomScreen
import com.lockwood.dwyw.core.screen.RoomsScreen
import com.lockwood.dwyw.core.ui.BaseActivity
import com.lockwood.replicant.screen.Screen
import com.lockwood.replicant.view.ProgressView
import com.lockwood.room.R
import com.lockwood.room.ui.fragment.RoomDetailFragment
import com.lockwood.room.ui.fragment.RoomsFragment

class RoomActivity : BaseActivity(), ProgressView, ActivityResultCallback<Boolean> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container_progress)

        if (savedInstanceState == null) {
            requestPermissions()
        }
    }

    override fun showScreen(screen: Screen) = when (screen) {
        is RoomsScreen -> showFragment(RoomsFragment.newInstance())
        is RoomScreen -> showFragment(RoomDetailFragment.newInstance(screen.id))
        else -> Unit
    }

    override fun showProgress() {
        findViewById<View>(R.id.progress_bar).visibility = View.VISIBLE
    }

    override fun hideProgress() {
        findViewById<View>(R.id.progress_bar).visibility = View.GONE
    }

    override fun onActivityResult(isGranted: Boolean) {
        // TODO: add Timber for logging
        Log.d(TAG, "onActivityResult: $isGranted")

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

    private fun showFragment(fragment: Fragment) {
        showFragment(R.id.fragment_container.toIdRes(), fragment)
    }

    private companion object {

        private val TAG = RoomActivity::class.simpleName
    }

}


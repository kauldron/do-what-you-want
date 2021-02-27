package com.lockwood.room.ui.activity

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.lockwood.dwyw.core.ui.BaseActivity
import com.lockwood.replicant.view.ProgressView
import com.lockwood.room.R

class RoomActivity : BaseActivity(), ProgressView, ActivityResultCallback<Boolean> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container_progress)

        if (savedInstanceState == null) {
            requestPermissions()
        }
    }

    override fun showProgress() {
        TODO("Not yet implemented")
    }

    override fun hideProgress() {
        TODO("Not yet implemented")
    }

    override fun onActivityResult(isGranted: Boolean) {
        // TODO: add Timber for logging
        Log.d(TAG, "onActivityResult: $isGranted")

        if (isGranted) {
//            showScreen(RoomsScreen)
        }
    }

    private fun requestPermissions() {
        val requestPermissionLauncher: ActivityResultLauncher<String> =
            registerForActivityResult(ActivityResultContracts.RequestPermission(), this)
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private companion object {

        private val TAG = RoomActivity::class.simpleName
    }

}


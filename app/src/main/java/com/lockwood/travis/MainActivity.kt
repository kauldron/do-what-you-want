package com.lockwood.travis

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), ActivityResultCallback<Boolean> {

    private val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions()
    }

    override fun onActivityResult(isGranted: Boolean) {
        Log.d(TAG, "onActivityResult: $isGranted")
    }

    private fun requestPermissions() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private companion object {

        val TAG = MainActivity::class.simpleName
    }
}
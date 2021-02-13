package com.lockwood.travis

import android.Manifest
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.p2p.WifiP2pManager.ChannelListener
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), ActivityResultCallback<Boolean>, ChannelListener {

    private val isSupportWifiDirect: Boolean
        get() = packageManager.hasSystemFeature(PackageManager.FEATURE_WIFI_DIRECT)

    private val isP2pSupported: Boolean
        get() = (applicationContext.getSystemService(WIFI_SERVICE) as WifiManager).isP2pSupported

    // TODO: add getSystemService ktx
    // TODO: move to p2p package
    private val wifiP2pManager by lazy { getSystemService(WIFI_P2P_SERVICE) as WifiP2pManager }
    private val retryChannel by lazy { wifiP2pManager.initialize(this, mainLooper, null) }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions()
    }

    override fun onActivityResult(isGranted: Boolean) {
        // TODO: add Timber for logging
        Log.d(TAG, "onActivityResult: $isGranted")

        if (isGranted) {
            if (initP2p()) {
                // TODO: add and Use Toaster
                Toast.makeText(this, "P2p is supported", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "P2p is not supported", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun requestPermissions() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onChannelDisconnected() {
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

    private companion object {

        val TAG = MainActivity::class.simpleName
    }

}

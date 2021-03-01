package com.lockwood.dwyw

import android.os.Bundle
import com.lockwood.dwyw.core.ui.BaseActivity
import com.lockwood.room.ui.launcher.RoomsLauncher

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        launchRooms()
    }

    private fun launchRooms() {
        // TODO: Use get feature
        RoomsLauncher(this).launch()
        finish()
    }

}

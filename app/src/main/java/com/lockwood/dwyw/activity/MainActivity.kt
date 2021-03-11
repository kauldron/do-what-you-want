package com.lockwood.dwyw.activity

import android.os.Bundle
import com.lockwood.dwyw.R
import com.lockwood.dwyw.core.screen.OnboardingScreen
import com.lockwood.dwyw.core.screen.RoomScreen
import com.lockwood.dwyw.core.screen.RoomsScreen
import com.lockwood.dwyw.core.ui.BaseActivity
import com.lockwood.replicant.screen.Screen
import com.lockwood.room.feature.RoomsFeature
import com.lockwood.room.launcher.RoomArgs
import com.lockwood.room.launcher.RoomArgs.Companion.toRoomArgs

class MainActivity : BaseActivity() {

    // TODO: Add ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        showScreen(RoomsScreen)
    }

    override fun showScreen(screen: Screen) = when (screen) {
        // TODO: Add OnboardingScreen
        is OnboardingScreen -> Unit
        is RoomsScreen -> doWithFinish { launchRooms() }
        is RoomScreen -> doWithFinish { launchRoom(screen.id.toRoomArgs()) }
        // TODO: Show and log error
        else -> Unit
    }

    private fun launchRooms() {
        getFeature<RoomsFeature>().roomsLauncher.launch(this)
    }

    private fun launchRoom(args: RoomArgs) {
        getFeature<RoomsFeature>().roomLauncher.launch(this, args)
    }

    private inline fun doWithFinish(action: () -> Unit) {
        action()
        finish()
    }

}
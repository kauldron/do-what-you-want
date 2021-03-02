package com.lockwood.dwyw

import android.os.Bundle
import com.lockwood.dwyw.core.screen.OnboardingScreen
import com.lockwood.dwyw.core.screen.RoomScreen
import com.lockwood.dwyw.core.screen.RoomsScreen
import com.lockwood.dwyw.core.ui.BaseActivity
import com.lockwood.replicant.screen.Screen
import com.lockwood.room.ui.launcher.RoomArgs.Companion.toRoomArgs
import com.lockwood.room.ui.launcher.RoomLauncher
import com.lockwood.room.ui.launcher.RoomsLauncher

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        showScreen(RoomsScreen)
    }

    override fun showScreen(screen: Screen) = when (screen) {
        // TODO: Use get feature for launchers
        // TODO: Add OnboardingScreen
        is OnboardingScreen -> Unit
        is RoomsScreen -> doWithFinish { RoomsLauncher(this).launch() }
        is RoomScreen -> doWithFinish { RoomLauncher(this).launch(screen.id.toRoomArgs()) }
        // TODO: Show and log error
        else -> Unit
    }

    private inline fun doWithFinish(action: () -> Unit) {
        action()
        finish()
    }

}

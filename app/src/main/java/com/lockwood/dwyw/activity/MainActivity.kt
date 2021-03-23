package com.lockwood.dwyw.activity

import android.os.Bundle
import com.lockwood.dwyw.core.screen.OnboardingScreen
import com.lockwood.dwyw.core.screen.RoomScreen
import com.lockwood.dwyw.core.screen.RoomsScreen
import com.lockwood.dwyw.core.ui.BaseActivity
import com.lockwood.replicant.screen.Screen
import com.lockwood.room.client.launcher.RoomArgs
import com.lockwood.room.client.launcher.RoomArgs.Companion.toRoomArgs
import com.lockwood.room.feature.RoomsFeature

class MainActivity : BaseActivity() {

  // TODO: Add ActionBar

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(com.lockwood.dwyw.ui.core.R.style.AppTheme)
    super.onCreate(savedInstanceState)

    showScreen(RoomsScreen)
  }

  override fun showScreen(screen: Screen) {
    return when (screen) {
      // TODO: Add OnboardingScreen
      is OnboardingScreen -> Unit
      is RoomsScreen -> doWithFinish { launchRooms() }
      is RoomScreen -> doWithFinish { launchRoom(screen.id.toRoomArgs()) }
      else -> super.showScreen(screen)
    }
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

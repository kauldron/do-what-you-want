package com.lockwood.dwyw.activity

import android.os.Bundle
import com.lockwood.dwyw.core.screen.OnboardingScreen
import com.lockwood.dwyw.core.ui.BaseActivity
import com.lockwood.replicant.screen.Screen
import com.lockwood.room.feature.RoomsFeature
import com.lockwood.room.launcher.IRoomsLauncher
import com.lockwood.room.launcher.RoomArgs
import com.lockwood.room.screen.RoomConnectionScreen
import com.lockwood.room.screen.RoomsAdvertisingScreen
import com.lockwood.room.screen.RoomsDiscoveryScreen

class MainActivity : BaseActivity() {

  // TODO: Add ActionBar

  private val roomsLauncher: IRoomsLauncher
    get() = getFeature<RoomsFeature>().roomsLauncher

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(com.lockwood.dwyw.ui.core.R.style.AppTheme)
    super.onCreate(savedInstanceState)

    showScreen(RoomsDiscoveryScreen)
  }

  override fun showScreen(screen: Screen) {
    return when (screen) {
      // TODO: Add OnboardingScreen
      is OnboardingScreen -> Unit
      is RoomsDiscoveryScreen -> doWithFinish { roomsLauncher.launch(this) }
      is RoomsAdvertisingScreen -> doWithFinish { roomsLauncher.launchAdvertising(this) }
      is RoomConnectionScreen -> doWithFinish { roomsLauncher.launch(this, RoomArgs(screen.id)) }
      else -> super.showScreen(screen)
    }
  }

  private inline fun doWithFinish(action: () -> Unit) {
    action()
    finish()
  }
}

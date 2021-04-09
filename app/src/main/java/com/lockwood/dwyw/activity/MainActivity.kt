package com.lockwood.dwyw.activity

import android.os.Bundle
import com.lockwood.dwyw.core.ui.BaseActivity
import com.lockwood.replicant.screen.Screen
import com.lockwood.room.feature.RoomsFeature
import com.lockwood.room.navigation.launcher.IRoomsLauncher
import com.lockwood.room.navigation.router.IRoomsRouter
import com.lockwood.room.screen.RoomConnectionScreen
import com.lockwood.room.screen.RoomsAdvertisingScreen
import com.lockwood.room.screen.RoomsDiscoveryScreen

class MainActivity : BaseActivity() {

	private val roomsRouter: IRoomsRouter
		get() = getFeature<RoomsFeature>().roomsRouter

	private val roomsLauncher: IRoomsLauncher
		get() = getFeature<RoomsFeature>().roomsLauncher

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		showScreen(roomsRouter.getScreenToShow())
	}

	override fun showScreen(screen: Screen) {
		with(roomsLauncher) {
			return when (screen) {
				is RoomsDiscoveryScreen -> doWithFinish { launch(this@MainActivity) }
				is RoomsAdvertisingScreen -> doWithFinish { launchAdvertising(this@MainActivity) }
				is RoomConnectionScreen -> doWithFinish { launchRoom(screen.room, this@MainActivity) }
				else -> super.showScreen(screen)
			}
		}
	}

	private inline fun doWithFinish(action: () -> Unit) {
		action()
		finish()
	}
}

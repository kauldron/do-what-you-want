package com.lockwood.dwyw.activity

import android.os.Bundle
import com.lockwood.dwyw.R
import com.lockwood.dwyw.core.ui.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test)
    }

//	override fun onCreate(savedInstanceState: Bundle?) {
//		super.onCreate(savedInstanceState)
//
//		showScreen(getFeature<RoomsFeature>().roomsRouter.getScreenToShow())
//	}
//
//	override fun showScreen(screen: Screen) {
//		with(getFeature<RoomsFeature>().roomsLauncher) {
//			return when (screen) {
//				is DiscoveryScreen -> doWithFinish { launch(this@MainActivity) }
//				is AdvertisingScreen -> doWithFinish { launchAdvertising(this@MainActivity) }
//				is ConnectionScreen -> doWithFinish { launchRoom(screen.room, this@MainActivity) }
//				else -> super.showScreen(screen)
//			}
//		}
//	}
//
//	private inline fun doWithFinish(action: () -> Unit) {
//		action()
//		finish()
//	}
}

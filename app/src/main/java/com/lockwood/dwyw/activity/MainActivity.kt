package com.lockwood.dwyw.activity

import android.os.Bundle
import com.lockwood.dwyw.core.ui.BaseActivity
import com.lockwood.replicant.screen.Screen
import com.lockwood.room.feature.RoomsFeature

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val router = getFeature<RoomsFeature>().roomsRouter
//        showScreen(router.getScreenToShow())
    }

    override fun showScreen(screen: Screen) {
        with(getFeature<RoomsFeature>().roomsLauncher) {
            launch(this@MainActivity)
//            return when (screen) {
//                is DiscoveryScreen -> doWithFinish { launch(this@MainActivity) }
//                is AdvertisingScreen -> doWithFinish { launchAdvertising(this@MainActivity) }
//                is ConnectionScreen -> doWithFinish { launchRoom(screen.room, this@MainActivity) }
//                else -> super.showScreen(screen)
//            }
        }
    }

    private inline fun doWithFinish(action: () -> Unit) {
        action()
        finish()
    }

}

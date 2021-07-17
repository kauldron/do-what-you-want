package com.lockwood.dwyw

import android.app.Application
import com.lockwood.automata.android.ApplicationContext
import com.lockwood.connections.feature.ConnectionsFeature
import com.lockwood.dwyw.core.feature.CoreFeature
import com.lockwood.dwyw.logging.Logger
import com.lockwood.dwyw.wrapper.AppWrapperFeature
import com.lockwood.player.feature.PlayerFeature
import com.lockwood.recorder.feature.RecorderFeature
import com.lockwood.room.feature.RoomsFeature

class MainApplication() : Application(), DoWhatYouWantApplication {

    override val applicationContext: ApplicationContext = ApplicationContext(context = this)

    override val connectionsFeature = ConnectionsFeature(contextProvider = this)

    override val coreFeature = CoreFeature(contextProvider = this)

    override val recorderFeature = RecorderFeature(contextProvider = this)

    override val playerFeature = PlayerFeature()

    override val wrapperFeature = AppWrapperFeature()

    override val roomsFeature: RoomsFeature = RoomsFeature(
        playerManager = playerFeature.playerManager,
        buildConfigWrapper = wrapperFeature.buildConfigWrapper,
        connectionsManager = connectionsFeature.nearbyConnectionsManager
    )

    override fun onCreate() {
        super.onCreate()
        Logger.plantDebugTree()
    }

}

package com.lockwood.dwyw

import android.app.Application
import com.lockwood.automata.android.ApplicationContext
import com.lockwood.connections.feature.ConnectionsFeature
import com.lockwood.dwyw.core.feature.CoreFeature
import com.lockwood.dwyw.core.wrapper.WrapperFeature
import com.lockwood.dwyw.logging.Logger
import com.lockwood.dwyw.wrapper.AppWrapperFeature
import com.lockwood.player.feature.PlayerFeature
import com.lockwood.recorder.feature.RecorderFeature
import com.lockwood.room.feature.RoomsFeature

class MainApplication() : Application(), DoWhatYouWantApplication {

	override val applicationContext: ApplicationContext = ApplicationContext(context = this)

	override val connectionsFeature: ConnectionsFeature = ConnectionsFeature(contextProvider = this)

	override val wrapperFeature: WrapperFeature = AppWrapperFeature()

	override val playerFeature: PlayerFeature = PlayerFeature()

	override val recorderFeature: RecorderFeature = RecorderFeature(contextProvider = this)

	override val coreFeature: CoreFeature = CoreFeature()

	override val roomsFeature: RoomsFeature = RoomsFeature(
			contextProvider = this,
			playerManager = playerFeature.playerManager,
			connectionsManager = connectionsFeature.nearbyConnectionsManager,
			buildConfigWrapper = wrapperFeature.buildConfigWrapper,
			executorProvider = coreFeature.executorProvider
	)

	override fun onCreate() {
		super.onCreate()
		Logger.plantDebugTree()
	}

}

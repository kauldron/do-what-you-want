package com.lockwood.dwyw

import android.app.Application
import com.lockwood.automata.android.ApplicationContext
import com.lockwood.connections.feature.ConnectionsFeature
import com.lockwood.dwyw.logging.Logging
import com.lockwood.room.feature.RoomsFeature

class MainApplication : Application(), DoWhatYouWantApplication {

  override val applicationContext: ApplicationContext = ApplicationContext(this)

  override val connectionsFeature = ConnectionsFeature(this)

  override val roomsFeature = RoomsFeature(connectionsFeature.nearbyConnectionsManager)

  override fun onCreate() {
    super.onCreate()
    Logging.plantDebugTree()
  }
}

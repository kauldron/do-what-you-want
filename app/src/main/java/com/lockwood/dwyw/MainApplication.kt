package com.lockwood.dwyw

import android.app.Application
import com.lockwood.automata.android.ApplicationContext
import com.lockwood.direct.feature.DirectFeature
import com.lockwood.dwyw.logging.Logging
import com.lockwood.room.feature.RoomsFeature

class MainApplication : Application(), DoWhatYouWantApplication {

  override val applicationContext: ApplicationContext = ApplicationContext(this)

  override val roomsFeature = RoomsFeature()

  override val wifiDirectFeature = DirectFeature(this)

  override fun onCreate() {
    super.onCreate()
    Logging.plantDebugTree()
  }
}

package com.lockwood.dwyw

import android.app.Application
import com.lockwood.automata.android.ApplicationContext
import com.lockwood.dwyw.core.feature.CoreFeature
import com.lockwood.dwyw.core.feature.wrapper.WrapperFeature
import com.lockwood.dwyw.logging.Logger
import com.lockwood.dwyw.wrapper.AppWrapperFeature

class MainApplication : Application(), DoWhatYouWantApplication {

    override val applicationContext: ApplicationContext = ApplicationContext(context = this)

//	override val connectionsFeature: ConnectionsFeature = ConnectionsFeature(contextProvider = this)

//	override val recorderFeature: RecorderFeature = RecorderFeature(contextProvider = this)

    override val wrapperFeature: WrapperFeature = AppWrapperFeature()

//	override val playerFeature: PlayerFeature = PlayerFeature()

    override val coreFeature: CoreFeature = CoreFeature()

    override fun onCreate() {
        super.onCreate()
        Logger.plantDebugTree()
    }

}

package com.lockwood.direct.feature

import com.lockwood.automata.core.notSafeLazy
import com.lockwood.direct.WifiDirectManager
import com.lockwood.direct.WifiDirectManagerImpl
import com.lockwood.direct.reciver.WifiDirectReceiverManager
import com.lockwood.replicant.context.ApplicationContextProvider
import com.lockwood.replicant.feature.Feature
import com.lockwood.replicant.receiver.ReceiverManager

class DirectFeature(
  private val contextProvider: ApplicationContextProvider,
) : Feature {

  val wifiReceiverManager: ReceiverManager by notSafeLazy { WifiDirectReceiverManager() }

  val wifiDirectManager: WifiDirectManager by notSafeLazy {
    WifiDirectManagerImpl(contextProvider.applicationContext)
  }
}

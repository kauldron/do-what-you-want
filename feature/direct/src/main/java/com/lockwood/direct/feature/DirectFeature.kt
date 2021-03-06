package com.lockwood.direct.feature

import com.lockwood.automata.android.ApplicationContext
import com.lockwood.direct.WifiDirectManager
import com.lockwood.direct.WifiDirectManagerImpl
import com.lockwood.direct.reciver.WifiDirectReceiverManager
import com.lockwood.replicant.feature.Feature
import com.lockwood.replicant.receiver.ReceiverManager

class DirectFeature(private val context: ApplicationContext) : Feature {

    val wifiReceiverManager: ReceiverManager by lazy { WifiDirectReceiverManager(wifiDirectManager) }

    val wifiDirectManager: WifiDirectManager by lazy { WifiDirectManagerImpl(context) }

}
package com.lockwood.room

import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest
import com.lockwood.direct.WifiDirectManager
import com.lockwood.direct.WifiP2pActionListener
import com.lockwood.direct.WifiP2pError
import com.lockwood.dwyw.core.BaseViewModel
import com.lockwood.replicant.event.MessageEvent
import com.lockwood.room.DirectState.*
import com.lockwood.room.screen.DirectNotAvailableScreen
import com.lockwood.room.screen.P2pErrorScreen
import timber.log.Timber

internal class RoomDirectViewModel(
  private val wifiDirectManager: WifiDirectManager,
) : BaseViewModel<RoomDirectViewState>(RoomDirectViewState.initialState) {

  fun initP2p() {
    try {
      wifiDirectManager.init()
    } catch (e: IllegalStateException) {
      navigateTo(DirectNotAvailableScreen)
      return
    }

    requestServices()
  }

  private fun requestServices() {
    mutateState { state.copy(directState = REQUEST_SERVICES) }
    wifiDirectManager.requestServices(
      serviceRequest = WifiP2pDnsSdServiceRequest.newInstance(),
      listener = p2pListenerWithSuccess { discoverServices() }
    )
  }

  private fun discoverServices() {
    mutateState { state.copy(directState = DISCOVER_SERVICES) }
    wifiDirectManager.discoverServices(listener = p2pListenerWithSuccess { startRegistration() })
  }

  private fun startRegistration() {
    mutateState { state.copy(directState = ADD_LOCAL_SERVICES) }
    // TODO: Get last unused port
    val record: Map<String, String> =
      mapOf(
        "listenport" to 113.toString(),
        "buddyname" to "John Doe${(Math.random() * 1000).toInt()}",
        "available" to "visible"
      )

    // TODO: Add ._tcp and ._udp feature toggle
    val transportProtocol = "udp"
    val serviceInfo =
      WifiP2pDnsSdServiceInfo.newInstance("_test", "_presence._$transportProtocol", record)

    wifiDirectManager.addLocalService(
      serviceInfo = serviceInfo,
      listener = p2pListenerWithSuccess { offerEvent { MessageEvent("addLocalService") } }
    )
  }

  private inline fun p2pListenerWithSuccess(
    crossinline doOnSuccess: () -> Unit,
  ) =
    object : WifiP2pActionListener {

      override fun onSuccess() {
        Timber.d("${state.directState} onSuccess")
        doOnSuccess()
      }

      override fun onFailure(error: WifiP2pError) {
        Timber.e("${state.directState} onFailure with reason $error")
        navigateTo(P2pErrorScreen(error))
      }
    }
}

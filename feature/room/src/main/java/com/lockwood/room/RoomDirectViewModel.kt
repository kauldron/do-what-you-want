package com.lockwood.room

import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest
import com.lockwood.direct.WifiDirectManager
import com.lockwood.direct.WifiP2pActionListener
import com.lockwood.direct.WifiP2pError
import com.lockwood.dwyw.core.BaseViewModel
import com.lockwood.room.DirectState.*
import com.lockwood.room.screen.DirectNotAvailableScreen
import com.lockwood.room.screen.P2pErrorScreen
import timber.log.Timber

internal class RoomDirectViewModel(
  private val wifiDirectManager: WifiDirectManager,
) : BaseViewModel<RoomDirectViewState>(RoomDirectViewState.initialState), WifiP2pActionListener {

  override fun onSuccess() {
    Timber.d("WifiP2pActionListener onSuccess")
  }

  override fun onFailure(error: WifiP2pError) {
    Timber.e("WifiP2pActionListener onFailure with reason $error")
    navigateTo(P2pErrorScreen(error))
  }

  fun initP2p() {
    try {
      wifiDirectManager.init()
    } catch (e: IllegalStateException) {
      navigateTo(DirectNotAvailableScreen)
      return
    }

    addResponseListener()
    startRegistration()
  }

  private fun requestServices() {
    mutateState { state.copy(directState = REQUEST_SERVICES) }
    val serviceRequest = WifiP2pDnsSdServiceRequest.newInstance()
    wifiDirectManager.requestServices(serviceRequest = serviceRequest, listener = this)

    discoverServices()
  }

  private fun discoverServices() {
    mutateState { state.copy(directState = DISCOVER_SERVICES) }
    wifiDirectManager.discoverServices(listener = this)
  }

  private fun startRegistration() {
    mutateState { state.copy(directState = ADD_LOCAL_SERVICES) }
    wifiDirectManager.addLocalService(listener = this)
  }

  private fun addResponseListener() {
    wifiDirectManager.setDnsSdResponseListeners(
      listener = { instanceName, registrationType, srcDevice ->
        Timber.d(
          "DnsSd Service: instanceName - $instanceName; registrationType - $registrationType; srcDevice: - $srcDevice"
        )
      },
      textListener = { fullDomainName, txtRecordMap, srcDevice ->
        Timber.d(
          "DnsSd TextListener: fullDomainName - $fullDomainName; txtRecordMap - $txtRecordMap; srcDevice: - $srcDevice"
        )
      }
    )
  }
}

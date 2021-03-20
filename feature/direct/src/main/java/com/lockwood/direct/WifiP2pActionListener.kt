package com.lockwood.direct

interface WifiP2pActionListener {

  fun onSuccess()

  fun onFailure(error: WifiP2pError)
}

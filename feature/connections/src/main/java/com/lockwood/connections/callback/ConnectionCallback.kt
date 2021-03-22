package com.lockwood.connections.callback

interface ConnectionCallback {

  fun onConnectionInitiated()

  fun onConnectionResult()

  fun onDisconnected()
}

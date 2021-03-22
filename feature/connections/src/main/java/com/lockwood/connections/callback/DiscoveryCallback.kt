package com.lockwood.connections.callback

interface DiscoveryCallback {

  fun onEndpointFound()

  fun onEndpointLost()
}

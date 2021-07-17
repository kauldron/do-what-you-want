package com.lockwood.room.feature.host.ui

internal interface HostView {

    fun requestCapture()

    fun onCaptureGranted(isGranted: Boolean)
}
package com.lockwood.room.feature.host.ui

internal interface IHostView {

    fun requestCapture()

    fun onCaptureGranted(isGranted: Boolean)
}
package com.lockwood.recorder.ext

import android.content.Context
import android.content.Intent
import android.media.projection.MediaProjectionManager

fun Context.createScreenCaptureIntent(): Intent {
    val projectionManager = getSystemService(MediaProjectionManager::class.java)
    return projectionManager.createScreenCaptureIntent()
}
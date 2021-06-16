package com.lockwood.recorder.manager

import android.content.Context
import android.content.Intent
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager

interface IMediaProjectionManager {

    fun handleMediaProjectionResult(resultCode: Int, resultData: Intent)

    fun getCurrentMediaProjection(): MediaProjection?

    companion object {

        @JvmStatic
        fun createScreenCaptureIntent(context: Context): Intent {
            val projectionManager = context.getSystemService(MediaProjectionManager::class.java)
            return projectionManager.createScreenCaptureIntent()
        }
    }

}
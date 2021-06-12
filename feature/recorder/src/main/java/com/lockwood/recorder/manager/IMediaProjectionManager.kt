package com.lockwood.recorder.manager

import android.content.Context
import android.content.Intent
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import com.lockwood.automata.android.getSystemServiceSafe

interface IMediaProjectionManager {

    fun handleMediaProjectionResult(resultCode: Int, resultData: Intent)

    fun getCurrentMediaProjection(): MediaProjection?

    companion object {

        @JvmStatic
        fun createScreenCaptureIntent(context: Context): Intent {
            val projectionManager = context.getSystemServiceSafe<MediaProjectionManager>()
            return projectionManager.createScreenCaptureIntent()
        }
    }

}
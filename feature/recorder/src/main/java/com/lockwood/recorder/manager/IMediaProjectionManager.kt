package com.lockwood.recorder.manager

import android.content.Intent
import android.media.projection.MediaProjection

interface IMediaProjectionManager {

    fun handleMediaProjectionResult(resultCode: Int, resultData: Intent)

    fun getCurrentMediaProjection(): MediaProjection?
}
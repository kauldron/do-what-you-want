package com.lockwood.recorder.manager

import android.content.Intent
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import com.lockwood.automata.android.ApplicationContext
import com.lockwood.automata.android.getSystemService

internal class MediaProjectionManager(
    private val context: ApplicationContext,
) : IMediaProjectionManager {

    private var capturedMediaProjection: MediaProjection? = null

    override fun handleMediaProjectionResult(resultCode: Int, resultData: Intent) {
        val mediaProjectionManager = context.value.getSystemService<MediaProjectionManager>()
        capturedMediaProjection = mediaProjectionManager.getMediaProjection(resultCode, resultData)
    }

    override fun getCurrentMediaProjection(): MediaProjection? {
        return capturedMediaProjection
    }

}
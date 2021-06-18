package com.lockwood.recorder.factory

import android.media.AudioAttributes
import android.media.AudioPlaybackCaptureConfiguration
import android.media.AudioRecord
import android.media.projection.MediaProjection
import android.os.Build
import com.lockwood.dwyw.core.media.AudioParams

internal object AudioRecordFactory : IAudioRecordFactory {

    override fun make(projection: MediaProjection): AudioRecord {
        return AudioRecord.Builder()
            .setAudioFormat(AudioParams.recordFormat)
            .setBufferSizeInBytes(AudioParams.minRecordBufferedSize)
            .setAudioPlaybackCaptureConfig(projection)
            .build()
    }

    private fun AudioRecord.Builder.setAudioPlaybackCaptureConfig(
        projection: MediaProjection,
    ): AudioRecord.Builder = apply {
        val config = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            AudioPlaybackCaptureConfiguration.Builder(projection)
                .addMatchingUsage(AudioAttributes.USAGE_MEDIA)
                .addMatchingUsage(AudioAttributes.USAGE_UNKNOWN)
                .build()
        } else {
            error("${Build.VERSION.SDK_INT} < ${Build.VERSION_CODES.Q}")
        }

        setAudioPlaybackCaptureConfig(config)
    }

}
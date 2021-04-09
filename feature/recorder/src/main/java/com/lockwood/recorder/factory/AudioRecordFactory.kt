package com.lockwood.recorder.factory

import android.media.AudioAttributes
import android.media.AudioPlaybackCaptureConfiguration
import android.media.AudioRecord
import android.media.projection.MediaProjection
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import com.lockwood.dwyw.core.media.AudioParams

internal object AudioRecordFactory : IAudioRecordFactory {

	override fun make(projection: MediaProjection): AudioRecord {
		return if (VERSION.SDK_INT >= VERSION_CODES.M) {
			AudioRecord.Builder()
					.setAudioFormat(AudioParams.recordFormat)
					.setBufferSizeInBytes(AudioParams.minRecordBufferedSize)
					.setAudioPlaybackCaptureConfig(projection)
					.build()
		} else {
			error("${VERSION.SDK_INT} < ${VERSION_CODES.M}")
		}
	}

	private fun AudioRecord.Builder.setAudioPlaybackCaptureConfig(
			projection: MediaProjection
	): AudioRecord.Builder = apply {
		val config = if (VERSION.SDK_INT >= VERSION_CODES.Q) {
			AudioPlaybackCaptureConfiguration.Builder(projection)
					.addMatchingUsage(AudioAttributes.USAGE_MEDIA)
					.addMatchingUsage(AudioAttributes.USAGE_UNKNOWN)
					.build()
		} else {
			error("${VERSION.SDK_INT} < ${VERSION_CODES.Q}}")
		}
		setAudioPlaybackCaptureConfig(config)
	}

}
package com.lockwood.player.factory

import android.media.AudioAttributes
import android.media.AudioTrack
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import com.lockwood.dwyw.core.media.AudioParams

internal object AudioTrackFactory : IAudioTrackFactory {

	private val audioAttributes: AudioAttributes
		get() = AudioAttributes.Builder()
				.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
				.setUsage(AudioAttributes.USAGE_MEDIA)
				.build()

	override fun make(): AudioTrack {
		return AudioTrack.Builder()
				.setAudioAttributes(audioAttributes)
				.setAudioFormat(AudioParams.playAudioFormat)
				.setBufferSizeInBytes(AudioParams.minRecordBufferedSize)
				.setTransferMode(AudioTrack.MODE_STREAM)
				.setPowerSavingPerformanceMode()
				.build()
	}

	private fun AudioTrack.Builder.setPowerSavingPerformanceMode(): AudioTrack.Builder = apply {
		if (VERSION.SDK_INT >= VERSION_CODES.O) {
			setPerformanceMode(AudioTrack.PERFORMANCE_MODE_POWER_SAVING)
		}
	}

}
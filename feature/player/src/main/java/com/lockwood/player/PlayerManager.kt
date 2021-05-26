package com.lockwood.player

import android.media.AudioTrack
import timber.log.Timber

internal class PlayerManager(
		@JvmField
		private val audioTrack: AudioTrack,
) : IPlayerManager {

	override var isPlaying: Boolean = false
		private set

	override fun play() {
		if (!isPlaying) {
			audioTrack.play()
			isPlaying = true
		}
	}

	override fun stop() {
		if (isPlaying) {
			isPlaying = false
			audioTrack.pause()
		}
	}

	override fun write(byteArray: ByteArray) {
		try {
			audioTrack.write(byteArray, 0, byteArray.size)
		} catch (e: IllegalStateException) {
			Timber.e(e)
		}
	}

	override fun release() {
		stop()

		try {
			audioTrack.release()
		} catch (e: Exception) {
			Timber.e(e)
		}
	}

}

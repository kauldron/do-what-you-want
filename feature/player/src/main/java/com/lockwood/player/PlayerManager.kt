package com.lockwood.player

import android.media.AudioTrack

internal class PlayerManager(
		private val audioTrack: AudioTrack
) : IPlayerManager {

//	private companion object {
//
//		private val preferredBufferSize = AudioParams.minPlayBufferedSize
//	}

	private var isPlaying: Boolean = false

	private val preferredBufferSize = audioTrack.bufferSizeInFrames

	override fun getIsPlaying(): Boolean {
		return isPlaying
	}

	override fun play() {
		if (!isPlaying) {
			audioTrack.play()
			isPlaying = true
		}
	}

	override fun stop() {
		if (isPlaying) {
			audioTrack.pause()
			isPlaying = false
		}
	}

	override fun write(byteArray: ByteArray) {
		audioTrack.write(byteArray, 0, preferredBufferSize, AudioTrack.WRITE_NON_BLOCKING)
	}

	override fun release() {
		stop()
		audioTrack.release()
	}

}

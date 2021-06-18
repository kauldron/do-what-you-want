package com.lockwood.player

import android.media.AudioTrack
import android.util.Log

internal class PlayerManager(
    private val audioTrack: AudioTrack,
) : IPlayerManager {

    private companion object {

        private const val TAG = "PlayerManager"
    }

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
            Log.e(TAG, e.message.toString())
        }
    }

    override fun release() {
        stop()

        try {
            audioTrack.release()
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }
    }

}

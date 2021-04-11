package com.lockwood.dwyw.core.media

import android.media.AudioFormat
import android.media.AudioRecord

object AudioParams {

	private const val DEFAULT_RATE_HZ = 41000
	private const val DEFAULT_RECORD_CHANNEL = AudioFormat.CHANNEL_IN_STEREO
	private const val DEFAULT_PLAY_CHANNEL = AudioFormat.CHANNEL_OUT_STEREO
	private const val DEFAULT_FORMAT = AudioFormat.ENCODING_PCM_16BIT

	val playAudioFormat: AudioFormat
		get() = AudioFormat.Builder()
				.setChannelMask(DEFAULT_PLAY_CHANNEL)
				.setSampleRate(DEFAULT_RATE_HZ)
				.setEncoding(DEFAULT_FORMAT)
				.build()

	val recordFormat: AudioFormat
		get() = AudioFormat.Builder()
				.setChannelMask(DEFAULT_RECORD_CHANNEL)
				.setSampleRate(DEFAULT_RATE_HZ)
				.setEncoding(DEFAULT_FORMAT)
				.build()

	val minRecordBufferedSize: Int
		get() = AudioRecord.getMinBufferSize(DEFAULT_RATE_HZ, DEFAULT_RECORD_CHANNEL, DEFAULT_FORMAT)

}
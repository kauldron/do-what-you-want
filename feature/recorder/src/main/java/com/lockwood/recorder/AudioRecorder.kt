package com.lockwood.recorder

import android.media.AudioRecord
import com.lockwood.automata.core.notSafeLazy
import com.lockwood.dwyw.core.media.AudioParams
import com.lockwood.recorder.callback.RecordCallback
import com.lockwood.recorder.factory.AudioRecordFactory
import com.lockwood.recorder.manager.IMediaProjectionManager
import timber.log.Timber

internal class AudioRecorder(
		@JvmField
		private val manager: IMediaProjectionManager,
) : IAudioRecorder {

	private companion object {

		private val preferredBufferSize = AudioParams.minRecordBufferedSize
	}

	private var isRecording: Boolean = false

	private val listeners: MutableList<RecordCallback> = mutableListOf()

	private val byteArray = ByteArray(preferredBufferSize)

	private val audioRecord: AudioRecord by notSafeLazy {
		val manager = manager.getCurrentMediaProjection()
		checkNotNull(manager)

		AudioRecordFactory.make(manager)
	}

	override fun getIsRecording(): Boolean {
		return isRecording
	}

	override fun read() {
		try {
			audioRecord.read(byteArray, 0, preferredBufferSize)
		} catch (e: IllegalStateException) {
			Timber.e(e)
			return
		}

		listeners.forEach { recordCallback -> recordCallback.onRead(byteArray) }
	}

	override fun start() {
		if (!isRecording) {
			audioRecord.startRecording()
			isRecording = true

			listeners.forEach(RecordCallback::onStartRecord)
		}
	}

	override fun stop() {
		if (isRecording) {
			isRecording = false
			audioRecord.stop()

			listeners.forEach(RecordCallback::onStopRecord)
		}
	}

	override fun addRecordCallback(callback: RecordCallback) {
		listeners.add(callback)
	}

	override fun removeRecordCallback(callback: RecordCallback) {
		listeners.remove(callback)
	}

	override fun release() {
		stop()
		listeners.clear()

		try {
			audioRecord.release()
		} catch (e: Exception) {
			Timber.e(e)
		}
	}

}

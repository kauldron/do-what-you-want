package com.lockwood.recorder

import com.lockwood.recorder.callback.RecordCallback
import com.lockwood.replicant.feature.Releasable

interface IAudioRecorder : Releasable {

    val isRecording: Boolean

    fun start()

    fun read()

    fun stop()

    fun addRecordCallback(callback: RecordCallback)

    fun removeRecordCallback(callback: RecordCallback)

}
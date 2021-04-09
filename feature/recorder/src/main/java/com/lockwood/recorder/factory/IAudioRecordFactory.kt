package com.lockwood.recorder.factory

import android.media.AudioRecord
import android.media.projection.MediaProjection

interface IAudioRecordFactory {

	fun make(projection: MediaProjection): AudioRecord

}
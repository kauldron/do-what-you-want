package com.lockwood.recorder.callback

interface RecordCallback {

	fun onStartRecord() = Unit

	fun onStopRecord() = Unit

	fun onRead(byteArray: ByteArray) = Unit
}
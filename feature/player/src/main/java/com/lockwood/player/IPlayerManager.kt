package com.lockwood.player

import com.lockwood.replicant.feature.Releasable

interface IPlayerManager : Releasable {

	val isPlaying: Boolean

	fun play()

	fun stop()

	fun write(byteArray: ByteArray)

}
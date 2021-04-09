package com.lockwood.player

import com.lockwood.replicant.feature.Releasable

interface IPlayerManager : Releasable {

	fun getIsPlaying(): Boolean

	fun play()

	fun stop()

	fun write(byteArray: ByteArray)

}
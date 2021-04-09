package com.lockwood.player.feature

import android.Manifest
import android.media.AudioTrack
import com.lockwood.automata.core.notSafeLazy
import com.lockwood.player.IPlayerManager
import com.lockwood.player.PlayerManager
import com.lockwood.player.factory.AudioTrackFactory
import com.lockwood.replicant.feature.PermissionsFeature
import com.lockwood.replicant.feature.ReleasableFeature

class PlayerFeature : ReleasableFeature, PermissionsFeature {

	override val requiredPermissions: Array<String>
		get() = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

	val playerManager: IPlayerManager by lazy {
		PlayerManager(audioTrack)
	}

	private val audioTrack: AudioTrack by notSafeLazy {
		AudioTrackFactory.make()
	}

	override fun release() {
		playerManager.release()
		super.release()
	}

}

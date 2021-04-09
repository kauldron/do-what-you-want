package com.lockwood.player.factory

import android.media.AudioTrack

interface IAudioTrackFactory {

	fun make(): AudioTrack

}
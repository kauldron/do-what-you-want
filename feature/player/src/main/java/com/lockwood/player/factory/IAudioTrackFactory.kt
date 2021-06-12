package com.lockwood.player.factory

import android.media.AudioTrack

internal interface IAudioTrackFactory {

    fun make(): AudioTrack

}
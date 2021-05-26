package com.lockwood.recorder.feature

import android.Manifest
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import com.lockwood.recorder.AudioRecorder
import com.lockwood.recorder.IAudioRecorder
import com.lockwood.recorder.manager.IMediaProjectionManager
import com.lockwood.recorder.manager.MediaProjectionManager
import com.lockwood.replicant.context.ApplicationContextProvider
import com.lockwood.replicant.feature.PermissionsFeature
import com.lockwood.replicant.feature.ReleasableFeature
import com.lockwood.replicant.releasable.notSafeReleasableLazy

class RecorderFeature(
		@JvmField
		private val contextProvider: ApplicationContextProvider,
) : ReleasableFeature, PermissionsFeature {

	override val requiredPermissions: Array<String>
		get() = if (isEnabled) {
			arrayOf(Manifest.permission.RECORD_AUDIO)
		} else {
			emptyArray()
		}

	override val isEnabled: Boolean
		get() = VERSION.SDK_INT >= VERSION_CODES.Q

	val mediaProjectionManager: IMediaProjectionManager by notSafeReleasableLazy {
		MediaProjectionManager(contextProvider.applicationContext)
	}

	val audioRecorder: IAudioRecorder by lazy {
		AudioRecorder(mediaProjectionManager)
	}

	override fun release() {
		audioRecorder.release()
		super.release()
	}

}

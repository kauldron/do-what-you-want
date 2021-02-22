package com.lockwood.replicant.launcher

import android.content.Context
import android.content.Intent
import com.lockwood.replicant.launcher.args.LaunchArgs

abstract class ContextLauncher<C : Context, T : LaunchArgs>(
	protected val context: C,
) : Launcher<T> {

	protected companion object {

		val EMPTY_INTENT: Intent
			get() = Intent()
	}

}
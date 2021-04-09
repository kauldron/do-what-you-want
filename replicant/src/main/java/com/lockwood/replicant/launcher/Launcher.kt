package com.lockwood.replicant.launcher

import android.content.Context
import com.lockwood.replicant.launcher.args.LaunchArgs

interface Launcher<T : LaunchArgs> {

	fun launch(context: Context, launchArgs: T)
}

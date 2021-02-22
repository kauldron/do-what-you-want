package com.lockwood.replicant.launcher

import android.content.Intent
import com.lockwood.replicant.launcher.args.LaunchArgs

interface Launcher<T : LaunchArgs> {

	fun launch(launchArgs: T)

	fun buildIntent(launchArgs: T): Intent
}
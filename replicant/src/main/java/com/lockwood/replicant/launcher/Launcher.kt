package com.lockwood.replicant.launcher

import com.lockwood.replicant.launcher.args.LaunchArgs

interface Launcher<T : LaunchArgs> {

    fun launch(launchArgs: T)
}

interface NoArgsLauncher {

    fun launch()
}
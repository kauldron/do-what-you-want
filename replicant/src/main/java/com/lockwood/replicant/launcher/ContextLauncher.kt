package com.lockwood.replicant.launcher

import android.content.Context
import android.os.Bundle
import com.lockwood.replicant.launcher.args.LaunchArgs

abstract class ContextLauncher<C : Context, T : LaunchArgs>(
    protected val context: C,
) : Launcher<T> {

    protected abstract fun buildBundle(launchArgs: T): Bundle
}

abstract class ContextNoArgsLauncher<C : Context>(
    protected val context: C,
) : NoArgsLauncher
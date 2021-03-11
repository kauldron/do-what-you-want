package com.lockwood.dwyw.logging

import com.lockwood.dwyw.BuildConfig
import timber.log.Timber
import timber.log.Timber.DebugTree

object Logging {

    @JvmStatic
    fun plantDebugTree() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

}
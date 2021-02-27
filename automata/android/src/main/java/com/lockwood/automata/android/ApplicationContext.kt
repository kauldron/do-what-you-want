package com.lockwood.automata.android

import android.app.Application
import android.content.Context

inline class ApplicationContext(
    private val application: Application,
) {

    val context: Context
        get() = application.applicationContext

    companion object {

        @JvmStatic
        fun Application.toContext(): ApplicationContext {
            return ApplicationContext(this)
        }
    }
}
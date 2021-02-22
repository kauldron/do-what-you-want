package com.lockwood.automata.android

import android.app.Application
import android.content.Context

inline class ApplicationContext(
    private val value: Application,
) {

    val context: Context
        get() = value.applicationContext
}

fun Application.toContext(): ApplicationContext {
    return ApplicationContext(this)
}
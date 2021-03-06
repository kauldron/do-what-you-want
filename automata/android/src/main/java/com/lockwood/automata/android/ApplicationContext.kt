package com.lockwood.automata.android

import android.app.Application
import android.content.Context

inline class ApplicationContext(
    private val context: Context,
) {

    val value: Context
        get() = application.applicationContext

    val application: Application
        get() = context.applicationContext as Application

}
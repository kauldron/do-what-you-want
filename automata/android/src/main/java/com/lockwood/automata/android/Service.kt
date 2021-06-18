package com.lockwood.automata.android

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log

inline fun <reified T : Service> Context.startService() {
    val intent = newIntent<T>(this)

    try {
        startService(intent)
    } catch (e: IllegalStateException) {
        Log.e("Service", e.message.toString())
    }
}

inline fun <reified T : Service> Context.startService(init: Intent.() -> Unit) {
    val intent = newIntent<T>(this)
    intent.init()

    try {
        startService(intent)
    } catch (e: IllegalStateException) {
        Log.e("Service", e.message.toString())
    }
}

inline fun <reified T : Service> Context.startForegroundService() {
    val intent = newIntent<T>(this)

    try {
        startForegroundServiceSafe(intent)
    } catch (e: IllegalStateException) {
        Log.e("Service", e.message.toString())
    }
}

inline fun <reified T : Service> Context.startForegroundService(
    init: Intent.() -> Unit,
) {
    val intent = newIntent<T>(this)
    intent.init()

    try {
        startForegroundServiceSafe(intent)
    } catch (e: IllegalStateException) {
        Log.e("Service", e.message.toString())
    }
}

fun Context.startForegroundServiceSafe(intent: Intent) {
    if (Build.VERSION.SDK_INT >= 26) {
        startForegroundService(intent)
    } else {
        startService(intent)
    }
}
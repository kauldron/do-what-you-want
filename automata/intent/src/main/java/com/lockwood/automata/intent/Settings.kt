package com.lockwood.automata.intent

import android.content.Context
import android.provider.Settings
import com.lockwood.automata.android.buildIntent

fun Context.openSettings() = buildIntent(Settings.ACTION_SETTINGS) {
    startActivity(this)
    return@buildIntent
}
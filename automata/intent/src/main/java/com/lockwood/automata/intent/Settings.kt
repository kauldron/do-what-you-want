package com.lockwood.automata.intent

import android.content.Context
import android.content.Intent
import android.provider.Settings
import com.lockwood.automata.android.buildIntent

fun Context.openSettings(): Intent {
	return buildIntent(Settings.ACTION_SETTINGS) {
		startActivity(this)
		return@buildIntent
	}
}

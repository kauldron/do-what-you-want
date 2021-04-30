package com.lockwood.automata.intent

import android.content.Context
import android.content.Intent
import android.provider.Settings

fun Context.openSettings(): Intent {
	return Intent(Settings.ACTION_SETTINGS).apply {
		startActivity(this)
	}
}

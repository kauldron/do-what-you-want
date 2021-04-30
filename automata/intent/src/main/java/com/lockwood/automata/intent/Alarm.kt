package com.lockwood.automata.intent

import android.content.Context
import android.content.Intent
import android.provider.AlarmClock

fun Context.createAlarm(
		message: String,
		hour: Int,
		minutes: Int,
): Intent {
	return Intent(AlarmClock.ACTION_SET_ALARM).apply {
		putExtra(AlarmClock.EXTRA_MESSAGE, message)
		putExtra(AlarmClock.EXTRA_HOUR, hour)
		putExtra(AlarmClock.EXTRA_MINUTES, minutes)

		startActivity(this)
	}
}

fun Context.startTimer(
		message: String,
		seconds: Int,
		skipUi: Boolean = false,
): Intent {
	return Intent(AlarmClock.ACTION_SET_TIMER).apply {
		putExtra(AlarmClock.EXTRA_MESSAGE, message)
		putExtra(AlarmClock.EXTRA_LENGTH, seconds)
		putExtra(AlarmClock.EXTRA_SKIP_UI, skipUi)

		startActivity(this)
	}
}

fun Context.showAlarms(): Intent {
	return Intent(AlarmClock.ACTION_SHOW_ALARMS).apply {
		startActivity(this)
	}
}

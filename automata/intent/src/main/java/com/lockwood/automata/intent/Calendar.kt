package com.lockwood.automata.intent

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract

fun Context.addCalendarEvent(
		title: String,
		location: String,
		begin: Long,
		end: Long,
): Intent {
	return Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI).apply {
		putExtra(CalendarContract.Events.TITLE, title)
		putExtra(CalendarContract.Events.EVENT_LOCATION, location)
		putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
		putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end)

		startActivity(this)
	}
}

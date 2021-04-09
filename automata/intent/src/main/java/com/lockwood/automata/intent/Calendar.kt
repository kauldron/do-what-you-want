package com.lockwood.automata.intent

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import com.lockwood.automata.android.buildIntent

fun Context.addCalendarEvent(
		title: String,
		location: String,
		begin: Long,
		end: Long,
): Intent {
	return buildIntent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI) {
		putExtra(CalendarContract.Events.TITLE, title)
		putExtra(CalendarContract.Events.EVENT_LOCATION, location)
		putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
		putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end)

		startActivity(this)
		return@buildIntent
	}
}

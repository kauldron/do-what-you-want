package com.lockwood.automata.intent

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.lockwood.automata.android.buildIntent

fun Context.openWebPage(url: String): Intent {
	return buildIntent(Intent.ACTION_VIEW, Uri.parse(url)) { startActivity(this) }
}

fun Context.searchWeb(query: String): Intent {
	return buildIntent(Intent.ACTION_WEB_SEARCH) {
		putExtra(SearchManager.QUERY, query)

		startActivity(this)
	}
}

package com.lockwood.automata.intent

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openWebPage(url: String): Intent {
	return Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply { startActivity(this) }
}

fun Context.searchWeb(query: String): Intent {
	return Intent(Intent.ACTION_WEB_SEARCH).apply {
		putExtra(SearchManager.QUERY, query)

		startActivity(this)
	}
}

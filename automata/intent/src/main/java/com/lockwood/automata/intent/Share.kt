package com.lockwood.automata.intent

import android.content.Context
import android.content.Intent
import com.lockwood.automata.android.buildIntent
import com.lockwood.automata.android.wrapWithChooser

fun Context.shareText(
		text: String,
): Intent {
	return buildIntent(Intent.ACTION_SEND) {
		type = "text/plain"
		putExtra(Intent.EXTRA_TEXT, text)

		val chooserIntent = wrapWithChooser()
		startActivity(chooserIntent)
	}
}

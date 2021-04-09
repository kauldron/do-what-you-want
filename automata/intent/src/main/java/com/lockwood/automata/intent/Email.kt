package com.lockwood.automata.intent

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.lockwood.automata.android.buildIntent
import com.lockwood.automata.core.EMPTY
import com.lockwood.automata.core.LINE_SEPARATOR
import com.lockwood.automata.core.SINGLE
import com.lockwood.automata.file.MimeTypes.WILDCARD

private val <Uri> Array<Uri>.sendAction: String
	get() {
		return if (size == Int.SINGLE) {
			Intent.ACTION_SEND
		} else {
			Intent.ACTION_SEND_MULTIPLE
		}
	}

fun Context.composeEmail(
		address: String,
		subject: String,
		body: String = String.EMPTY,
): Intent {
	return buildIntent(Intent.ACTION_SENDTO, buildValidMailUri(address, subject, body)) {
		startActivity(this)
		return@buildIntent
	}
}

fun Context.composeEmail(
		address: String,
		subject: String,
		vararg attachments: Uri,
) {
	composeEmail(
			addresses = arrayOf(address),
			subject = subject,
			attachments = attachments,
	)
}

fun Context.composeEmail(
		addresses: Array<String>,
		subject: String,
		vararg attachments: Uri,
) {
	val intent: Intent =
			buildIntent(attachments.sendAction) {
				type = WILDCARD
				putExtra(Intent.EXTRA_EMAIL, addresses)
				putExtra(Intent.EXTRA_SUBJECT, subject)
				putExtra(Intent.EXTRA_STREAM, attachments)
			}

	startActivity(intent)
}

private fun buildValidMailUri(
		address: String,
		subject: String,
		body: String = String.EMPTY,
): Uri {
	val mailUri = StringBuilder("mailto:$address?subject=$subject")

	if (body.isNotEmpty()) {
		val bodyWithLines = body.replace(String.LINE_SEPARATOR, "<br>")
		mailUri.append("&body=$bodyWithLines")
	}

	return Uri.parse(mailUri.toString())
}

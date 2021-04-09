package com.lockwood.automata.android

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.lockwood.automata.core.ZERO

@kotlin.jvm.Throws(ActivityNotFoundException::class)
fun Context.launchActivity(
		className: String,
		options: Bundle? = null,
		init: Intent.() -> Unit = {},
) {
	val intent = newIntent(this, className)
	intent.init()

	if (options != null) {
		intent.putExtras(options)
	}

	startActivity(intent)
}

@kotlin.jvm.Throws(ActivityNotFoundException::class)
inline fun <reified T : Any> Context.launchActivity(
		options: Bundle? = null,
		init: Intent.() -> Unit = {},
) {
	val intent = newIntent<T>(this)
	intent.init()

	if (options != null) {
		intent.putExtras(options)
	}

	startActivity(intent)
}

// Workaround for [Android Q Beta] Memory leak in IRequestFinishCallback$Stub
// https://issuetracker.google.com/issues/139738913
fun FragmentActivity.handleIRequestFinishCallbackMemoryLeak() {
	handleIRequestFinishCallbackMemoryLeak { onBackPressed() }
}

inline fun FragmentActivity.handleIRequestFinishCallbackMemoryLeak(action: () -> Unit) {
	if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
		if (isTaskRoot && supportFragmentManager.backStackEntryCount == Int.ZERO) {
			finishAfterTransition()
		}
	} else {
		action()
	}
}

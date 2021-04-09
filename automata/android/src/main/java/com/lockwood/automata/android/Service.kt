package com.lockwood.automata.android

import android.app.Service
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat

inline fun <reified T : Service> Context.startService() {
	val intent = newIntent<T>(this)

	try {
		startService(intent)
	} catch (e: IllegalStateException) {
		Log.e("Service", e.message.toString())
	}
}

inline fun <reified T : Service> Context.startService(init: Intent.() -> Unit) {
	val intent = newIntent<T>(this)
	intent.init()

	try {
		startService(intent)
	} catch (e: IllegalStateException) {
		Log.e("Service", e.message.toString())
	}
}

inline fun <reified T : Service> Context.stopService() {
	val intent = newIntent<T>(this)

	try {
		stopService(intent)
	} catch (e: IllegalStateException) {
		Log.e("Service", e.message.toString())
	}
}

inline fun <reified T : Service> Context.startForegroundService() {
	val intent = newIntent<T>(this)

	try {
		ContextCompat.startForegroundService(this, intent)
	} catch (e: IllegalStateException) {
		Log.e("Service", e.message.toString())
	}
}

inline fun <reified T : Service> Context.startForegroundService(
		init: Intent.() -> Unit,
) {
	val intent = newIntent<T>(this)
	intent.init()

	try {
		ContextCompat.startForegroundService(this, intent)
	} catch (e: IllegalStateException) {
		Log.e("Service", e.message.toString())
	}
}
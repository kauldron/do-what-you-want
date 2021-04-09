package com.lockwood.automata.android

import android.app.Application
import android.content.Context

inline class ApplicationContext(
		private val context: Context,
) {

	val value: Context
		get() = context

	val application: Application
		get() = context as Application

	override fun toString(): String {
		return super.toString()
	}
}

@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST", "CommitPrefEdits")

package com.lockwood.automata.android

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.lockwood.automata.core.EMPTY

@kotlin.jvm.Throws(IllegalArgumentException::class)
inline fun <T> SharedPreferences.findPreference(name: String, default: T): T {
	val res: Any? =
			when (default) {
				is Number -> getNumber(name, default)
				is String -> getString(name, default)
				is Boolean -> getBoolean(name, default)
				else -> throw IllegalArgumentException("This [$default] type cannot be saved in Preferences")
			}
	return res as T
}

@kotlin.jvm.Throws(IllegalArgumentException::class)
inline fun <T> SharedPreferences.putPreference(name: String, value: T) {
	with(edit()) {
		when (value) {
			is Number -> putNumber(name, value)
			is String -> putString(name, value)
			is Boolean -> putBoolean(name, value)
			else -> throw IllegalArgumentException("This [$value] type cannot be saved in Preferences")
		}.apply()
	}
}

@kotlin.jvm.Throws(IllegalArgumentException::class)
inline fun Editor.putNumber(
		key: String,
		value: Number,
): Editor {
	return when (value) {
		is Long -> putLong(key, value)
		is Int -> putInt(key, value)
		is Float -> putFloat(key, value)
		else -> throw IllegalArgumentException("This type ${value::class} of value cannot be saved in Preferences")
	}
}

@kotlin.jvm.Throws(IllegalArgumentException::class)
inline fun SharedPreferences.getNumber(
		key: String,
		default: Number,
): Number {
	return when (default) {
		is Long -> getLong(key, default)
		is Int -> getInt(key, default)
		is Float -> getFloat(key, default)
		else -> throw IllegalArgumentException("This type ${default::class} of value cannot be saved in Preferences")
	}
}

fun SharedPreferences.getStringOrEmpty(
		key: String,
): String = getString(key, String.EMPTY) ?: String.EMPTY

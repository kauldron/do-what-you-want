package com.lockwood.automata.android

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.lockwood.automata.core.EMPTY

@kotlin.jvm.Throws(IllegalArgumentException::class)
fun Editor.putNumber(
    key: String,
    value: Number,
): Editor = when (value) {
    is Long -> putLong(key, value)
    is Int -> putInt(key, value)
    is Float -> putFloat(key, value)
    else -> throw IllegalArgumentException("This type ${value::class} of value cannot be saved in Preferences")
}

@kotlin.jvm.Throws(IllegalArgumentException::class)
fun SharedPreferences.getNumber(
    key: String,
    default: Number,
): Number = when (default) {
    is Long -> getLong(key, default)
    is Int -> getInt(key, default)
    is Float -> getFloat(key, default)
    else -> throw IllegalArgumentException("This type ${default::class} of value cannot be saved in Preferences")
}

fun SharedPreferences.getStringOrEmpty(
    key: String,
): String = getString(key, String.EMPTY) ?: String.EMPTY
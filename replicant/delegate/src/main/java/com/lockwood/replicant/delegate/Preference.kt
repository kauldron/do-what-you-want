package com.lockwood.replicant.delegate

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.lockwood.automata.android.getNumber
import com.lockwood.automata.android.putNumber
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Preference<T>(
    private val prefs: SharedPreferences,
    private val name: String,
    private val default: T,
) : ReadWriteProperty<Any?, T> {

    override fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): T = prefs.findPreference(name, default)

    override fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: T,
    ) = prefs.putPreference(name, value)

    @Suppress("UNCHECKED_CAST")
    @kotlin.jvm.Throws(IllegalArgumentException::class)
    private fun SharedPreferences.findPreference(name: String, default: T): T {
        val res: Any? = when (default) {
            is Number -> getNumber(name, default)
            is String -> getString(name, default)
            is Boolean -> getBoolean(name, default)
            else -> throw IllegalArgumentException("This type cannot be saved in Preferences")
        }
        return res as T
    }

    @SuppressLint("CommitPrefEdits")
    @kotlin.jvm.Throws(IllegalArgumentException::class)
    private fun SharedPreferences.putPreference(name: String, value: T) {
        with(edit()) {
            when (value) {
                is Number -> putNumber(name, value)
                is String -> putString(name, value)
                is Boolean -> putBoolean(name, value)
                else -> throw IllegalArgumentException("This type cannot be saved in Preferences")
            }.apply()
        }
    }

}

inline fun <T> SharedPreferences.delegate(
    name: String,
    default: () -> T,
): Preference<T> = Preference(this, name, default.invoke())

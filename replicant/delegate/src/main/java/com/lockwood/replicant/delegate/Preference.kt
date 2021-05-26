package com.lockwood.replicant.delegate

import android.content.SharedPreferences
import com.lockwood.automata.android.findPreference
import com.lockwood.automata.android.putPreference
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Preference<T>(
		@JvmField
		private val prefs: SharedPreferences,
		@JvmField
		private val name: String,
		@JvmField
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

}

inline fun <T> SharedPreferences.delegate(
		name: String,
		default: () -> T,
): Preference<T> = Preference(this, name, default.invoke())

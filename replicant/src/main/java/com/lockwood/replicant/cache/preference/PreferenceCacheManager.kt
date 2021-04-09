package com.lockwood.replicant.cache.preference

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.lockwood.automata.android.findPreference
import com.lockwood.automata.android.putPreference
import com.lockwood.replicant.context.ApplicationContextProvider


class PreferenceCacheManager(
		contextProvider: ApplicationContextProvider,
) : IPreferenceCacheManager {

	override val appPreference: SharedPreferences by lazy {
		contextProvider.applicationContext.application.asDefaultSharedPreferences()
	}

	override fun <T> get(key: String, default: T): T {
		return appPreference.findPreference(key, default)
	}

	override fun <T> get(key: String, default: T, context: Context): T {
		return context.asDefaultSharedPreferences().findPreference(key, default)
	}

	override fun <T> put(key: String, value: T) {
		appPreference.putPreference(key, value)
	}

	override fun <T> put(key: String, value: T, context: Context) {
		context.asDefaultSharedPreferences().putPreference(key, value)
	}

	private fun Context.asDefaultSharedPreferences(): SharedPreferences {
		return PreferenceManager.getDefaultSharedPreferences(this)
	}

}
package com.lockwood.replicant.cache.preference

import android.content.Context
import android.content.SharedPreferences
import com.lockwood.replicant.cache.CacheManager

interface IPreferenceCacheManager : CacheManager {

	val appPreference: SharedPreferences

	fun <T> get(key: String, default: T, context: Context): T

	fun <T> put(key: String, value: T, context: Context)

}
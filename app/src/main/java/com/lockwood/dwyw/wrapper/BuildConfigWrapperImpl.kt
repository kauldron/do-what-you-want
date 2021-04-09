package com.lockwood.dwyw.wrapper

import android.os.Build
import com.lockwood.automata.core.SPACE
import com.lockwood.dwyw.BuildConfig
import com.lockwood.dwyw.core.wrapper.BuildConfigWrapper

class BuildConfigWrapperImpl : BuildConfigWrapper {

	override val versionCode: Int
		get() = BuildConfig.VERSION_CODE

	override val versionName: String
		get() = BuildConfig.VERSION_NAME

	override val isDebug: Boolean
		get() = BuildConfig.DEBUG

	override val appInfo: String
		get() = buildString {
			append("Name: $versionName")
			append(String.SPACE)
			append("($versionCode, ${BuildConfig.FLAVOR})")
		}

	override val deviceModel: String
		get() = Build.MODEL.toString()

}
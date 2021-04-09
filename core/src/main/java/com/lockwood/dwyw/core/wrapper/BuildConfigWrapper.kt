package com.lockwood.dwyw.core.wrapper

interface BuildConfigWrapper {

	val versionCode: Int

	val versionName: String

	val isDebug: Boolean

	val appInfo: String

	val deviceModel: String
}

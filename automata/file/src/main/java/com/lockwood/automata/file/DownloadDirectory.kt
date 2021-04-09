package com.lockwood.automata.file

import android.content.Context
import android.os.Build
import android.os.Environment
import com.lockwood.automata.core.NotEmptyString
import com.lockwood.automata.core.notEmptyString
import java.io.File
import java.io.IOException

@kotlin.jvm.Throws(IOException::class)
@Suppress("DEPRECATION")
fun Context.getExternalDirectory(
		type: NotEmptyString,
		useLegacyStorage: Boolean = false,
): File {
	return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q || useLegacyStorage) {
		Environment.getExternalStoragePublicDirectory(type.value)
	} else {
		getExternalFilesDir(type.value)
				?: throw IOException("External storage is not currently mounted")
	}
}

@kotlin.jvm.Throws(IOException::class)
@Suppress("DEPRECATION")
fun Context.getDownloadsExternalDirectory(
		useLegacyStorage: Boolean = false,
): File = getExternalDirectory(Environment.DIRECTORY_DOWNLOADS.notEmptyString(), useLegacyStorage)

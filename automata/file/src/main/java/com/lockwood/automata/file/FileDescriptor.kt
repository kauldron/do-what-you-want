package com.lockwood.automata.file

import android.content.ContentResolver
import android.net.Uri
import com.lockwood.automata.core.NotEmptyString
import com.lockwood.automata.core.notEmptyString
import java.io.FileDescriptor

inline fun ContentResolver.openFileDescriptor(
		uri: Uri,
		mode: NotEmptyString,
		action: FileDescriptor.() -> Unit,
) {
	val parcelFileDescriptor = requireNotNull(openFileDescriptor(uri, mode.value))
	val fileDescriptor = requireNotNull(parcelFileDescriptor.fileDescriptor)

	fileDescriptor.action()

	parcelFileDescriptor.close()
}

inline fun ContentResolver.openWriteFileDescriptor(
		uri: Uri,
		action: FileDescriptor.() -> Unit,
) = openFileDescriptor(uri = uri, mode = "w".notEmptyString(), action = action)

inline fun ContentResolver.openReadFileDescriptor(
		uri: Uri,
		action: FileDescriptor.() -> Unit,
) = openFileDescriptor(uri = uri, mode = "r".notEmptyString(), action = action)

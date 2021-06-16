package com.lockwood.automata.file

import android.content.ContentResolver
import android.net.Uri
import com.lockwood.automata.core.ext.notEmptyString
import com.lockwood.automata.core.wrappers.NotEmptyString
import java.io.FileDescriptor

inline fun ContentResolver.openFileDescriptor(
    uri: Uri,
    mode: NotEmptyString,
    action: FileDescriptor.() -> Unit,
) {
    val parcelFileDescriptor = requireNotNull(openFileDescriptor(uri, mode.toString()))
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

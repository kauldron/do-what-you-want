package com.lockwood.automata.file

import android.content.ContentResolver
import android.net.Uri
import java.io.FileDescriptor

inline fun ContentResolver.openFileDescriptor(
    uri: Uri,
    mode: String,
    action: FileDescriptor.() -> Unit,
) {
    val parcelFileDescriptor = requireNotNull(openFileDescriptor(uri, mode))
    val fileDescriptor = requireNotNull(parcelFileDescriptor.fileDescriptor)

    fileDescriptor.action()

    parcelFileDescriptor.close()
}

inline fun ContentResolver.openWriteFileDescriptor(
    uri: Uri,
    action: FileDescriptor.() -> Unit,
) = openFileDescriptor(
    uri = uri,
    mode = "w",
    action = action
)

inline fun ContentResolver.openReadFileDescriptor(
    uri: Uri,
    action: FileDescriptor.() -> Unit,
) = openFileDescriptor(
    uri = uri,
    mode = "r",
    action = action
)

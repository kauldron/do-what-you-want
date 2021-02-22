package com.lockwood.automata.file

import android.webkit.MimeTypeMap
import com.lockwood.automata.file.MimeTypes.WILDCARD
import java.io.File


val File.notExist: Boolean
    get() = !exists()

val File.extension: String
    get() = name.substringAfterLast(".")

val File.mimeType: String
    get() = MimeTypeMap.getSingleton().getExtensionFromMimeType(extension) ?: WILDCARD
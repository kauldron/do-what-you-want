package com.lockwood.automata.file

import com.lockwood.automata.core.wrappers.NotEmptyString
import java.io.File

val File.notExist: Boolean
    get() = !exists()

val File.extension: String
    get() = name.substringAfterLast(".")

val File.mimeType: NotEmptyString
    get() = MimeTypes.getMimeTypeFromExtension(extension)

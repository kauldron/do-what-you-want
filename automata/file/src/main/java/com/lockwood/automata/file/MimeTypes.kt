package com.lockwood.automata.file

import android.webkit.MimeTypeMap
import com.lockwood.automata.core.ext.notEmptyString
import com.lockwood.automata.core.wrappers.NotEmptyString

object MimeTypes {

    const val WILDCARD = "*/*"

    fun getFileExtensionFromUrl(url: String): String {
        return MimeTypeMap.getFileExtensionFromUrl(url)
    }

    @kotlin.jvm.Throws(IllegalArgumentException::class)
    fun getExtensionFromMimeType(mimeType: String): String {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
            ?: error("There is no extension from mimeType: $mimeType")
    }

    fun getMimeTypeFromExtension(extension: String): NotEmptyString {
        val type = MimeTypeMap.getSingleton().getExtensionFromMimeType(extension) ?: WILDCARD
        return type.notEmptyString()
    }
}

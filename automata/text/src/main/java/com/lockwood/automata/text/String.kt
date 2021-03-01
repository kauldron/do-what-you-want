package com.lockwood.automata.text

import com.lockwood.automata.core.SINGLE
import com.lockwood.automata.core.ZERO

private const val ELLIPSIZE_END_LIMIT = 3
private const val ELLIPSIZE_END_REPLACEMENT = "..."

fun String.ellipsizeEnd(
    maxLength: Int,
): String {
    if (length > ELLIPSIZE_END_LIMIT && length >= maxLength) {

        val oldText = removeRange(maxLength, length)
        return with(oldText) {
            replaceRange(
                startIndex = length - Int.SINGLE,
                endIndex = length,
                replacement = ELLIPSIZE_END_REPLACEMENT
            )
        }
    }

    return this
}

fun String.toUpperCaseFirstLetter(): String = if (isNotEmpty()) {
    val firstChar = first().toUpperCase().toString()
    replaceRange(
        startIndex = Int.ZERO,
        endIndex = Int.SINGLE,
        replacement = firstChar
    )
} else {
    this
}
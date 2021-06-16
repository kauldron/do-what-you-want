package com.lockwood.automata.core

private const val ELLIPSIZE_END_LIMIT = 3
private const val ELLIPSIZE_END_REPLACEMENT = "..."

val String.Companion.EMPTY
    get() = ""

val String.Companion.LINE_SEPARATOR
    get() = "\n"

val String.Companion.SPACE
    get() = "\u0020"

val String.Companion.NON_BREAKING_SPACE
    get() = "\u0020"

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

fun String.toUpperCaseFirstLetter(): String {
    return if (isNotEmpty()) {
        val firstChar = first().toUpperCase().toString()
        replaceRange(startIndex = Int.ZERO, endIndex = Int.SINGLE, replacement = firstChar)
    } else {
        this
    }
}

package com.lockwood.automata.core

inline infix fun <reified T> List<T>.contentEquals(other: List<T>): Boolean {
    if (this == other) return true
    if (this.size != other.size) return false

    forEachIndexed { index, element ->
        val otherElement = other[index]

        if (element != otherElement) {
            return false
        }
    }

    return true
}

inline fun <reified T> List<T>.contentHashCode(): Int {
    return toTypedArray().contentHashCode()
}

inline fun <reified T> List<T>.contentToString(): String {
    return joinToString(separator = ", ", prefix = "[", postfix = "]")
}

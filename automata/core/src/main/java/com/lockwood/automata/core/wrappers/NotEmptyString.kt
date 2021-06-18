package com.lockwood.automata.core.wrappers

@JvmInline
value class NotEmptyString(
    private val value: String,
) : Comparable<String>, CharSequence {

    init {
        require(value.isNotEmpty())
    }

    operator fun plus(other: Any?): NotEmptyString {
        return NotEmptyString(value.plus(other))
    }

    override val length: Int
        get() = value.length

    override fun get(index: Int): Char {
        return value[index]
    }

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
        return value.subSequence(startIndex, endIndex)
    }

    override fun toString(): String {
        return value
    }

    override fun compareTo(other: String): Int {
        return value.compareTo(other)
    }

}
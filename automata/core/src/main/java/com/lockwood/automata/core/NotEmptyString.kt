package com.lockwood.automata.core

@JvmInline
value class NotEmptyString(
    @JvmField
    private val value: String
) : Comparable<String>, CharSequence {

    init {
        require(value.isNotEmpty())
    }

    override val length: Int
        get() = value.length

    override fun get(index: Int) = value[index]

    override fun subSequence(startIndex: Int, endIndex: Int) =
        value.subSequence(startIndex, endIndex)

    override fun toString(): String = value

    override fun compareTo(other: String) = value.compareTo(other)

}

@kotlin.jvm.Throws(IllegalArgumentException::class)
fun CharSequence.notEmptyString(): NotEmptyString = NotEmptyString(toString())
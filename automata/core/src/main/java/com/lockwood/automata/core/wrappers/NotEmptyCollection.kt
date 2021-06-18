package com.lockwood.automata.core.wrappers

@JvmInline
value class NotEmptyCollection<T>(
    private val value: Collection<T>,
) : Collection<T> {

    init {
        require(value.isNotEmpty())
    }

    override val size: Int
        get() = value.size

    override fun contains(element: T): Boolean {
        return value.contains(element)
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return value.containsAll(elements)
    }

    override fun isEmpty(): Boolean {
        return false
    }

    override fun iterator(): Iterator<T> {
        return value.iterator()
    }

    override fun toString(): String {
        return value.toString()
    }

}
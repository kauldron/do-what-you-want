package com.lockwood.automata.core.wrappers

@JvmInline
value class NotEmptyList<T>(
    private val value: List<T>,
) : List<T> {

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

    override fun get(index: Int): T {
        return value[index]
    }

    override fun indexOf(element: T): Int {
        return value.indexOf(element)
    }

    override fun isEmpty(): Boolean {
        return false
    }

    override fun iterator(): Iterator<T> {
        return value.iterator()
    }

    override fun lastIndexOf(element: T): Int {
        return value.lastIndexOf(element)
    }

    override fun listIterator(): ListIterator<T> {
        return value.listIterator()
    }

    override fun listIterator(index: Int): ListIterator<T> {
        return value.listIterator(index)
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<T> {
        return value.subList(fromIndex, toIndex)
    }

    override fun toString(): String {
        return value.toString()
    }

}
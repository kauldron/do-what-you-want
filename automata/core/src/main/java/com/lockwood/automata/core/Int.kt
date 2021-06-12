package com.lockwood.automata.core

val Int.isOdd: Boolean
    get() = this and 0x01 != Int.ZERO

val Int.isEven: Boolean
    get() = !isOdd

val Int.Companion.SINGLE: Int
    get() = 1

val Int.Companion.BITES_OFFSET: Int
    get() = SIZE_BITS - 1

val Int.Companion.ZERO: Int
    get() = 0

val Int.Companion.UNDEFINED: Int
    get() = -1

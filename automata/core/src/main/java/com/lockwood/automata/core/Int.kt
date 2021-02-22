package com.lockwood.automata.core

val Int.isOdd: Boolean
    get() = this and 0x01 != Int.ZERO

val Int.isEven: Boolean
    get() = !isOdd

val Int.Companion.SINGLE: Int
    get() = 1

val Int.Companion.ZERO: Int
    get() = 0
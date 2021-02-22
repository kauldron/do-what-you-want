package com.lockwood.automata.core

inline fun <reified T : Any> newInstance(): T {
    return T::class.java.newInstance()
}
package com.lockwood.automata.core

fun <T> notSafeLazy(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)

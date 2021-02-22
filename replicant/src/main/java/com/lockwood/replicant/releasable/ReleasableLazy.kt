package com.lockwood.replicant.releasable

import kotlin.properties.ReadOnlyProperty

fun <T : Any> releasableLazy(initializer: () -> T): ReadOnlyProperty<Any?, T> {
	return SynchronizedReleasableLazy(initializer)
}

fun <T : Any> releasableLazy(
	mode: LazyThreadSafetyMode,
	initializer: () -> T,
): ReadOnlyProperty<Any?, T> {
	return when (mode) {
		LazyThreadSafetyMode.SYNCHRONIZED -> SynchronizedReleasableLazy(initializer)
		LazyThreadSafetyMode.NONE -> UnsafeReleasableLazy(initializer)
		else -> error("Unknown mode: $mode")
	}
}
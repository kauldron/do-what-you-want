package com.lockwood.replicant.ext

import com.lockwood.replicant.releasable.SynchronizedReleasableLazy
import com.lockwood.replicant.releasable.UnsafeReleasableLazy
import kotlin.properties.ReadOnlyProperty

fun <T : Any> releasableLazy(initializer: () -> T): ReadOnlyProperty<Any?, T> {
	return SynchronizedReleasableLazy(initializer)
}

fun <T : Any> notSafeReleasableLazy(initializer: () -> T): ReadOnlyProperty<Any?, T> {
	return UnsafeReleasableLazy(initializer)
}

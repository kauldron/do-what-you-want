@file:Suppress("UNCHECKED_CAST")

package com.lockwood.replicant.releasable

import com.lockwood.replicant.feature.Releasable
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class UnsafeReleasableLazy<T : Any>
@PublishedApi
internal constructor(
    private val initializer: () -> T,
) : ReadOnlyProperty<Any?, T>, Releasable {

    private var releasableValue: Any = UNINITIALIZED_VALUE

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (releasableValue === UNINITIALIZED_VALUE) {
            releasableValue = initializer()
        }

        return releasableValue as T
    }

    override fun release() {
        if (releasableValue === UNINITIALIZED_VALUE) {
            return
        }

        releasableValue = UNINITIALIZED_VALUE
    }
}

fun <T : Any> notSafeReleasableLazy(initializer: () -> T): ReadOnlyProperty<Any?, T> {
    return UnsafeReleasableLazy(initializer)
}

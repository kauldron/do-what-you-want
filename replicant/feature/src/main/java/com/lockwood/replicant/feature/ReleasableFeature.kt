@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package com.lockwood.replicant.feature

import androidx.annotation.CallSuper
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

private typealias ReleasableProperty = KProperty1<out ReleasableFeature, *>
private typealias Property = KProperty1<Any, *>

interface ReleasableFeature : Feature, Releasable {

    @CallSuper
    override fun release() {
        this::class
            .declaredMemberProperties
            .filter {
                val value = it.getValue()

                if (value is Releasable) {
                    value.release()
                }

                value !is Releasable
            }
            .mapNotNull { it.getDelegate() }
            .filterIsInstance<Releasable>()
            .forEach(Releasable::release)
    }

    private inline fun ReleasableProperty.getValue(): Any? {
        isAccessible = true
        val value = (this as Property).get(this@ReleasableFeature)
        isAccessible = false

        return value
    }

    private inline fun ReleasableProperty.getDelegate(): Any? {
        isAccessible = true
        val delegate = (this as Property).getDelegate(this@ReleasableFeature)
        isAccessible = false

        return delegate
    }
}

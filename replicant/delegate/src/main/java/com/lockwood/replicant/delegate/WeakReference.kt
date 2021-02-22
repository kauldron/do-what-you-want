package com.lockwood.replicant.delegate

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class WeakReference<T>(value: T? = null) : ReadWriteProperty<Any?, T> {

    private var weakReference = java.lang.ref.WeakReference<T>(value)

    @kotlin.jvm.Throws(IllegalArgumentException::class)
    override fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): T = weakReference.get() ?: error("${property.name} not init")

    @kotlin.jvm.Throws(IllegalArgumentException::class)
    override fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: T,
    ) {
        weakReference = java.lang.ref.WeakReference(value)
    }

}

inline fun <T> weakReference(
    value: () -> T?,
): WeakReference<T> = WeakReference(value.invoke())
@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package com.lockwood.replicant.delegate

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

interface Nullable {

    fun resetToNotNull()
}

class FakeNotNullVar<T> : ReadWriteProperty<Any?, T>, Nullable {

    private var value: T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: error("Property ${property.name} should be initialized before get")
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }

    override fun resetToNotNull() {
        this.value = null
    }

}

fun <T> fakeNotNull(): FakeNotNullVar<T> = FakeNotNullVar()

@kotlin.jvm.Throws(ClassCastException::class)
inline fun <reified T : Any> T.resetFakeNotNullVar() {
    this::class
        .declaredMemberProperties
        .mapNotNull { property ->
            property.isAccessible = true
            val delegate = (property as KProperty1<Any, *>).getDelegate(this)
            property.isAccessible = false

            delegate
        }
        .filterIsInstance(Nullable::class.java)
        .forEach(Nullable::resetToNotNull)
}
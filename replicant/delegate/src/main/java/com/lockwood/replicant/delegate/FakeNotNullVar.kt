package com.lockwood.replicant.delegate

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface Nullable {

	fun resetToNull()
}

class FakeNotNullVar<T> : ReadWriteProperty<Any?, T>, Nullable {

	private var value: T? = null

	override fun getValue(thisRef: Any?, property: KProperty<*>): T {
		return value ?: error("Property ${property.name} should be initialized before get")
	}

	override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
		this.value = value
	}

	override fun resetToNull() {
		this.value = null
	}

}

fun <T> fakeNotNull(): FakeNotNullVar<T> = FakeNotNullVar()

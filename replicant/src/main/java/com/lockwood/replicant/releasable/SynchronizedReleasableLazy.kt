@file:Suppress("UNCHECKED_CAST")

package com.lockwood.replicant.releasable

import com.lockwood.replicant.feature.Releasable
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class SynchronizedReleasableLazy<T : Any>
@PublishedApi
internal constructor(
		private val initializer: () -> T,
) : ReadOnlyProperty<Any?, T>, Releasable {

	@Volatile
	private var releasableValue: Any? = UNINITIALIZED_VALUE

	override fun getValue(thisRef: Any?, property: KProperty<*>): T {
		val _v1 = releasableValue
		if (_v1 !== UNINITIALIZED_VALUE) {
			return _v1 as T
		}

		return safeGetValue()
	}

	private fun safeGetValue(): T {
		synchronized(this) {
			val _v2 = releasableValue

			return if (_v2 !== UNINITIALIZED_VALUE) {
				_v2 as T
			} else {
				val typedValue = initializer()
				releasableValue = typedValue
				typedValue
			}
		}
	}

	override fun release() {
		val _v1 = releasableValue
		if (_v1 === UNINITIALIZED_VALUE) {
			return
		}

		synchronized(this) {
			val _v2 = releasableValue
			if (_v2 === UNINITIALIZED_VALUE) {
				return
			} else {
				releasableValue = UNINITIALIZED_VALUE
			}
		}
	}
}

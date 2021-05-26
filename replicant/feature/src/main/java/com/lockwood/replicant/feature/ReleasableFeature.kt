@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package com.lockwood.replicant.feature

import androidx.annotation.CallSuper
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

interface ReleasableFeature : Feature, Releasable {

	@CallSuper
	override fun release() {
		this::class
				.declaredMemberProperties
				.mapNotNull { it.getDelegate() }
				.filterIsInstance<Releasable>()
				.forEach { it.release() }
	}

	private inline fun KProperty1<out ReleasableFeature, *>.getDelegate(): Any? {
		isAccessible = true
		val delegate = (this as KProperty1<Any, *>).getDelegate(this@ReleasableFeature)
		isAccessible = false

		return delegate
	}
}

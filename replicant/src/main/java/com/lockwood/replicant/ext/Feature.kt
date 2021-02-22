@file:Suppress("UNCHECKED_CAST")

package com.lockwood.replicant.ext

import android.app.Application
import com.lockwood.replicant.feature.Feature
import com.lockwood.replicant.feature.ReleasableFeature
import java.lang.reflect.Type
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaType

var features: Map<Type, KProperty1<out Application, *>>? = null

inline fun <reified T : Feature> Application.getProperty(): KProperty1<Application, T> {
	if (features.isNullOrEmpty()) {
		features = this::class.declaredMemberProperties
			.map { it.returnType.javaType to it }
			.toMap()
	}

	return requireNotNull(features)[T::class.javaObjectType] as KProperty1<Application, T>
}

inline fun <reified T : Feature> Application.getFeature(): T {
	return getProperty<T>().get(this)
}

inline fun <reified T : ReleasableFeature> Application.releaseFeature() {
	getFeature<T>().release()
}
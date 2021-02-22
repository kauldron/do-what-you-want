package com.lockwood.replicant.feature

import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

interface Feature

interface ReleasableFeature : Feature, Releasable {

    override fun release() {
        this::class.declaredMemberProperties.mapNotNull { it.getDelegate() }
            .filterIsInstance<Releasable>()
            .forEach { it.release() }
    }

    @Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")
    private inline fun KProperty1<out ReleasableFeature, *>.getDelegate(): Any? {
        isAccessible = true
        val delegate = (this as KProperty1<Any, *>).getDelegate(this@ReleasableFeature)
        isAccessible = false

        return delegate
    }

}
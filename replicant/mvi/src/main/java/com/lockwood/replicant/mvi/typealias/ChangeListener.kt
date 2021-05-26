package com.lockwood.replicant.mvi.`typealias`

typealias StateChangeListener<T> = (state: T) -> Unit
typealias EffectChangeListener<T> = (effect: T) -> Unit

package com.lockwood.automata.core.ext

import com.lockwood.automata.core.wrappers.NotEmptyCollection
import com.lockwood.automata.core.wrappers.NotEmptyList

@kotlin.jvm.Throws(IllegalArgumentException::class)
fun <T> Collection<T>.notEmptyCollection(): NotEmptyCollection<T> = NotEmptyCollection(this)

@kotlin.jvm.Throws(IllegalArgumentException::class)
fun <T> List<T>.notEmptyList(): NotEmptyList<T> = NotEmptyList(this)
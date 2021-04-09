package com.lockwood.automata.core

inline class NotEmptyString(val value: String) {
	override fun toString(): String = value
}

@kotlin.jvm.Throws(IllegalArgumentException::class)
fun String.notEmptyString(): NotEmptyString {
	require(this.isNotEmpty())
	return NotEmptyString(this)
}

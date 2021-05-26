package com.lockwood.automata.core

inline fun <reified T : Any> newInstance(): T {
	return T::class.java.getDeclaredConstructor().newInstance()
}

fun <T> calculateHashCode(vararg data: T): Int {
	var result = data.first().hashCode()

	if (data.size > Int.SINGLE) {
		data.sliceArray(IntRange(Int.SINGLE, data.size - Int.SINGLE)).forEach {
			result = Int.BITES_OFFSET * result + it.hashCode()
		}
	}

	return result
}
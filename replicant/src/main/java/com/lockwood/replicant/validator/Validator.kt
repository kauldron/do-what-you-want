package com.lockwood.replicant.validator

interface Validator<T : Any> {

	fun isValid(value: T): Boolean
}

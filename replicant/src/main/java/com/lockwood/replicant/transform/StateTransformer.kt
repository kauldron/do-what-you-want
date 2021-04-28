package com.lockwood.replicant.transform

interface StateTransformer<VS> {

	fun <T> accept(data: T, state: VS): VS
}
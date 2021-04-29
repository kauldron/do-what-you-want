package com.lockwood.replicant.transform

// TODO: Rename to StateReducer
interface StateTransformer<VS> {

	// TODO: Replace data with action
	fun <T> accept(data: T, state: VS): VS
}

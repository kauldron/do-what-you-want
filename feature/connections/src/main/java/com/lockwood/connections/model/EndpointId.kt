package com.lockwood.connections.model

inline class EndpointId(
		@JvmField
		private val value: String,
) {
	override fun toString(): String = value
}
package com.lockwood.connections.model

inline class EndpointId(
		private val value: String
) {
	override fun toString(): String = value
}
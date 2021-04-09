package com.lockwood.connections.model

class ConnectionInfo(
		val isIncomingConnection: Boolean,
		val authenticationToken: String,
		val endpointName: String
)
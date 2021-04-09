package com.lockwood.connections.model

import com.google.android.gms.common.api.Status

sealed class ConnectionsStatus(
		status: Status,
		private val message: String = status.statusMessage.toString()
) {
	override fun toString(): String = message
}

class ConnectionSuccess(status: Status) : ConnectionsStatus(status)
class ConnectionRejected(status: Status) : ConnectionsStatus(status)
class ConnectionError(status: Status) : ConnectionsStatus(status)
class ConnectionUnknownStatus(status: Status) : ConnectionsStatus(status)


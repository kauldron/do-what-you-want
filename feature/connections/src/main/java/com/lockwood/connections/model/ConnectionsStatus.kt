package com.lockwood.connections.model

import com.google.android.gms.common.api.Status

sealed class ConnectionsStatus(
		private val status: Status,
) {
	override fun toString(): String = status.statusMessage.toString()
}

class ConnectionSuccess(status: Status) : ConnectionsStatus(status)
class ConnectionRejected(status: Status) : ConnectionsStatus(status)
class ConnectionError(status: Status) : ConnectionsStatus(status)
class ConnectionUnknownStatus(status: Status) : ConnectionsStatus(status)


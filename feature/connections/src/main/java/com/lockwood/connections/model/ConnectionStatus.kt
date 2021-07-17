package com.lockwood.connections.model

import com.google.android.gms.common.api.Status

sealed class ConnectionStatus(
    private val status: Status,
) {

    class Success(status: Status) : ConnectionStatus(status)
    class Rejected(status: Status) : ConnectionStatus(status)
    class Error(status: Status) : ConnectionStatus(status)
    class Unknown(status: Status) : ConnectionStatus(status)

    override fun toString(): String = requireNotNull(status.statusMessage).toString()
}




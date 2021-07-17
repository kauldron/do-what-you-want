package com.lockwood.connections.advertising

import com.lockwood.connections.callback.ConnectionCallback
import com.lockwood.connections.model.EndpointId

interface IAdvertisingConnectionsManager {

    fun startAdvertising(name: String)

    fun sendPayload(byteArray: ByteArray)

    fun requestConnection(name: String, endpointId: EndpointId)

    fun addConnectionCallback(callback: ConnectionCallback)

    fun removeConnectionCallback(callback: ConnectionCallback)

    fun stopAdvertising()

}
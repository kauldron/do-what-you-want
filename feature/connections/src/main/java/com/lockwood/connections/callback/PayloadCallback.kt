package com.lockwood.connections.callback

import com.lockwood.connections.model.EndpointId

interface PayloadCallback {

    fun onPayloadReceived(endpointId: EndpointId, byteArray: ByteArray) = Unit

    fun onPayloadTransferUpdate(endpointId: EndpointId) = Unit

}

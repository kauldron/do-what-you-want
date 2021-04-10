package com.lockwood.connections.callback

import com.lockwood.connections.model.EndpointId
import java.io.InputStream

interface PayloadCallback {

	fun onPayloadReceived(endpointId: EndpointId) = Unit

	fun onPayloadTransferUpdate(endpointId: EndpointId, inputStream: InputStream) = Unit

}

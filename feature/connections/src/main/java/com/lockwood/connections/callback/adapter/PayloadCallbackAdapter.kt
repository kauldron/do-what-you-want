package com.lockwood.connections.callback.adapter

import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.nearby.connection.PayloadTransferUpdate
import com.lockwood.connections.callback.PayloadCallback
import com.lockwood.connections.model.EndpointId
import timber.log.Timber

private typealias NearbyPayloadCallback = com.google.android.gms.nearby.connection.PayloadCallback

internal class PayloadCallbackAdapter :
		NearbyPayloadCallback(), CallbackAdapter<PayloadCallback> {

	private val listeners: MutableList<PayloadCallback> = mutableListOf()

	// TODO: Add OneWayMapper<NearbyPayload, Payload>
	override fun onPayloadReceived(id: String, payload: Payload) {
		Timber.d(
				"onPayloadReceived = endpointId: $id; payload: ${payload.asBytes()?.size}}"
		)

		val endpointId = EndpointId(id)
		val byteArray = payload.asBytes() ?: return

		listeners.forEach { it.onPayloadReceived(endpointId, byteArray) }
	}

	override fun onPayloadTransferUpdate(id: String, transferUpdate: PayloadTransferUpdate) {
		val endpointId = EndpointId(id)

		listeners.forEach { it.onPayloadTransferUpdate(endpointId) }
	}

	override fun addListener(callback: PayloadCallback) {
		listeners.add(callback)
	}

	override fun removeListener(callback: PayloadCallback) {
		listeners.remove(callback)
	}

}

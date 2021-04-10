package com.lockwood.connections.callback.adapter

import androidx.collection.SimpleArrayMap
import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.nearby.connection.PayloadTransferUpdate
import com.lockwood.automata.core.toHexString
import com.lockwood.connections.callback.PayloadCallback
import com.lockwood.connections.model.EndpointId
import com.lockwood.connections.model.NearbyPayloadCallback
import java.io.InputStream
import timber.log.Timber


internal class PayloadCallbackAdapter :
		NearbyPayloadCallback(), CallbackAdapter<PayloadCallback> {

	private val incomingPayloads: SimpleArrayMap<Long, Payload> = SimpleArrayMap()

	private val listeners: MutableList<PayloadCallback> = mutableListOf()

	override fun onPayloadReceived(id: String, payload: Payload) {
		incomingPayloads.put(payload.id, payload);
		Timber.d("onPayloadReceived: ${payload.id}")

		val endpointId = EndpointId(id)

		listeners.forEach { it.onPayloadReceived(endpointId) }
	}

	override fun onPayloadTransferUpdate(id: String, transferUpdate: PayloadTransferUpdate) {
		Timber.d("onPayloadTransferUpdate: ${transferUpdate.status}")

		if (transferUpdate.status == PayloadTransferUpdate.Status.SUCCESS) {
			val endpointId = EndpointId(id)
			val payload = incomingPayloads[transferUpdate.payloadId]
			payload ?: return

			Timber.d("onPayloadTransfer: ${payload.asInputStream().readBytes().toHexString()}")

			listeners.forEach { it.onPayloadTransferUpdate(endpointId, payload.asInputStream()) }
		}
	}

	override fun addListener(callback: PayloadCallback) {
		listeners.add(callback)
	}

	override fun removeListener(callback: PayloadCallback) {
		listeners.remove(callback)
	}

	private fun Payload.asInputStream(): InputStream {
		return requireNotNull(asStream()).asInputStream()
	}

}

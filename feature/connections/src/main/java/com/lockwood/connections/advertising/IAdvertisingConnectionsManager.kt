package com.lockwood.connections.advertising

import androidx.annotation.WorkerThread
import com.google.android.gms.tasks.Task
import com.lockwood.connections.callback.ConnectionCallback
import com.lockwood.connections.model.EndpointId

interface IAdvertisingConnectionsManager {

	fun startAdvertising(name: String): Task<Void>

	@WorkerThread
	fun sendPayload(byteArray: ByteArray)

	fun requestConnection(name: String, endpointId: EndpointId): Task<Void>

	fun addConnectionCallback(callback: ConnectionCallback)

	fun removeConnectionCallback(callback: ConnectionCallback)

	fun stopAdvertising()

}
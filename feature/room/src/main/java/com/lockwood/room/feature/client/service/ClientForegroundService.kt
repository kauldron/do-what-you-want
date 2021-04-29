package com.lockwood.room.feature.client.service

import android.app.Notification
import android.content.Intent
import androidx.annotation.WorkerThread
import com.lockwood.connections.callback.PayloadCallback
import com.lockwood.connections.model.EndpointId
import com.lockwood.dwyw.core.feature.CoreFeature
import com.lockwood.player.IPlayerManager
import com.lockwood.player.feature.PlayerFeature
import com.lockwood.replicant.executor.provider.ExecutorProvider
import com.lockwood.room.base.BaseRoomService
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.feature.RoomsFeature
import java.util.concurrent.ExecutorService

internal class ClientForegroundService : BaseRoomService() {

	private companion object {

		private const val NOTIFICATION_ID = 1080
	}

	private val readerExecutor: ExecutorService by lazy {
		executorProvider.io()
	}

	private val payloadCallback = object : PayloadCallback {
		override fun onPayloadReceived(endpointId: EndpointId, byteArray: ByteArray) {
			handleData(byteArray)
		}
	}

	private val channelId: String
		get() = ClientForegroundService::class.java.simpleName

	private val roomsInteractor: IRoomsInteractor
		get() = getFeature<RoomsFeature>().roomsInteractor

	private val executorProvider: ExecutorProvider
		get() = getFeature<CoreFeature>().executorProvider

	private val playerManager: IPlayerManager
		get() = getFeature<PlayerFeature>().playerManager

	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		startForeground()
		playerManager.play()

		return START_STICKY
	}

	override fun onCreate() {
		super.onCreate()
		roomsInteractor.addPayloadCallback(payloadCallback)
	}

	override fun onDestroy() {
		super.onDestroy()
		with(roomsInteractor) {
			stopDiscovery()
			resetCacheState()
			removePayloadCallback(payloadCallback)
		}

		readerExecutor.shutdown()

		releaseFeature<PlayerFeature>()
	}

	private fun startForeground() = with(roomsInteractor.connectedRoom) {
		val notification: Notification = buildNotification(channelId) {
			setContentTitle("Connected to $name")
			setTicker("Connected to $name")
			setContentText("You are listening other audio channel")
		}

		startForeground(NOTIFICATION_ID, notification)
	}

	@WorkerThread
	private fun handleData(byteArray: ByteArray) = readerExecutor.execute {
		playerManager.write(byteArray)
	}

}

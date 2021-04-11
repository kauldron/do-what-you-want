package com.lockwood.room.feature.client.service

import android.app.Notification
import android.content.Intent
import androidx.annotation.WorkerThread
import com.lockwood.connections.callback.PayloadCallback
import com.lockwood.connections.model.EndpointId
import com.lockwood.dwyw.core.feature.CoreFeature
import com.lockwood.player.IPlayerManager
import com.lockwood.player.feature.PlayerFeature
import com.lockwood.replicant.executor.ExecutorProvider
import com.lockwood.room.base.BaseRoomService
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.feature.RoomsFeature
import java.io.InputStream
import java.util.concurrent.Executor
import timber.log.Timber

internal class ClientForegroundService : BaseRoomService() {

	private companion object {

		private const val NOTIFICATION_ID = 1080
	}

	private val readerExecutor: Executor by lazy {
		executorProvider.io()
	}

	private val payloadCallback = object : PayloadCallback {
		override fun onPayloadTransferUpdate(endpointId: EndpointId, inputStream: InputStream) {
			handleData(inputStream)
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
		roomsInteractor.stopDiscovery()
		roomsInteractor.resetCacheState()
		roomsInteractor.removePayloadCallback(payloadCallback)
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
	private fun handleData(inputStream: InputStream) = readerExecutor.execute {
		while (playerManager.getIsPlaying()) {
			val byteArray = inputStream.readBytes()
			if (byteArray.isNotEmpty()) {
				Timber.d("handleData: ${byteArray.size}")
				playerManager.write(byteArray)
			}
		}
	}

}

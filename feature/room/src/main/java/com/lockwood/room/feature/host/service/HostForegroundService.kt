package com.lockwood.room.feature.host.service

import android.app.Notification
import android.content.Intent
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.google.android.gms.common.api.ApiException
import com.lockwood.dwyw.core.feature.CoreFeature
import com.lockwood.dwyw.core.feature.wrapper.WrapperFeature
import com.lockwood.recorder.IAudioRecorder
import com.lockwood.recorder.callback.RecordCallback
import com.lockwood.recorder.feature.RecorderFeature
import com.lockwood.replicant.executor.provider.ExecutorProvider
import com.lockwood.room.base.BaseRoomService
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.feature.RoomsFeature
import java.util.concurrent.ExecutorService


internal class HostForegroundService : BaseRoomService() {

	private companion object {

		private const val NOTIFICATION_ID = 720
		private const val STATUS_ALREADY_ADVERTISING = 8001
	}

	private val recorderExecutor: ExecutorService by lazy {
		executorProvider.io()
	}

	private val payloadExecutor: ExecutorService by lazy {
		executorProvider.io()
	}

	private val recordCallback = object : RecordCallback {
		override fun onStartRecord() {
			recordData()
		}

		override fun onRead(byteArray: ByteArray) {
			shareData(byteArray)
		}
	}

	private val channelId: String
		get() = HostForegroundService::class.java.simpleName

	private val deviceName: String
		get() = getFeature<WrapperFeature>().buildConfigWrapper.deviceModel

	private val roomsInteractor: IRoomsInteractor
		get() = getFeature<RoomsFeature>().roomsInteractor

	private val audioRecorder: IAudioRecorder
		get() = getFeature<RecorderFeature>().audioRecorder

	private val executorProvider: ExecutorProvider
		get() = getFeature<CoreFeature>().executorProvider

	override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
		return if (isStopService(intent)) {
			stopForegroundSelf()

			START_NOT_STICKY
		} else {
			startForeground()
			startAdvertising()

			START_STICKY
		}
	}

	private fun startAdvertising() {
		roomsInteractor.startAdvertising(deviceName)
				.addOnFailureListener {
					if (it is ApiException && it.statusCode == STATUS_ALREADY_ADVERTISING) {
						return@addOnFailureListener
					}

					showToast(it.message.toString())
				}
	}

	override fun onCreate() {
		super.onCreate()
		audioRecorder.addRecordCallback(recordCallback)
	}

	override fun onDestroy() {
		super.onDestroy()
		releaseSelf()
	}

	private fun startForeground() {
		val notification: Notification =
				buildNotification(channelId) {
					setContentTitle("Broadcasting as $deviceName")
					setContentText("You are broadcasting your audio channel")
					setTicker("Broadcasting as $deviceName")
				}

		startForeground(NOTIFICATION_ID, notification)
	}

	private fun releaseSelf() {
		roomsInteractor.stopAdvertising()
		audioRecorder.removeRecordCallback(recordCallback)

		recorderExecutor.shutdown()
		payloadExecutor.shutdown()

		releaseFeature<RecorderFeature>()
	}

	@WorkerThread
	private fun recordData() = recorderExecutor.execute {
		while (audioRecorder.getIsRecording()) {
			audioRecorder.read()
		}
	}

	@WorkerThread
	private fun shareData(byteArray: ByteArray) = payloadExecutor.execute {
		roomsInteractor.sendPayload(byteArray)
	}

	@MainThread
	private fun showToast(string: String) = executorProvider.main().execute {
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
	}

}
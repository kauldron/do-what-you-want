package com.lockwood.room.service

import android.app.Notification
import android.content.Intent
import com.lockwood.room.feature.RoomsFeature

class HostForegroundService : BaseRoomService() {

  private companion object {

    private const val NOTIFICATION_ID = 720
  }

  private val channelId: String
    get() = HostForegroundService::class.java.simpleName

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    startForeground()
    shareData()

    return START_STICKY
  }

  override fun onDestroy() {
    getFeature<RoomsFeature>().roomsInteractor.stopAdvertising()
  }

  private fun startForeground() {
    // TODO: Show host playing notification
    val notification: Notification =
      buildNotification(channelId) {
        setContentTitle("Broadcasting")
        setContentText("You are broadcasting your audio channel")
        setTicker("You are broadcasting your audio channel")
      }

    startForeground(NOTIFICATION_ID, notification)
  }

  private fun shareData() {
    // do heavy work on a background thread
  }
}

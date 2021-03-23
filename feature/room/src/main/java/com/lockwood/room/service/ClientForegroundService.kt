package com.lockwood.room.service

import android.app.Notification
import android.content.Intent

class ClientForegroundService : BaseRoomService() {

  private companion object {

    private const val NOTIFICATION_ID = 1080
  }

  private val channelId: String
    get() = ClientForegroundService::class.java.simpleName

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    startForeground()
    handleData()

    return START_STICKY
  }

  private fun startForeground() {
    val notification: Notification =
      buildNotification(channelId) {
        setContentTitle("Connected")
        setContentText("You are connected to another audio channel")
        setTicker("You are connected to another audio channel")
      }

    startForeground(NOTIFICATION_ID, notification)
  }

  private fun handleData() {
    // do heavy work on a background thread
  }
}

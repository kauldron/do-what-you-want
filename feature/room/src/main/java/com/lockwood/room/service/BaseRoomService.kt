package com.lockwood.room.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.lockwood.replicant.base.ReplicantService
import com.lockwood.room.RoomConnectionActivity

abstract class BaseRoomService : ReplicantService() {

  protected val pendingIntent: PendingIntent
    get() {
      val notificationIntent = Intent(this, RoomConnectionActivity::class.java)
      return PendingIntent.getActivity(this, 0, notificationIntent, 0)
    }

  protected inline fun buildNotification(
    channelId: String,
    onBuild: NotificationCompat.Builder.() -> NotificationCompat.Builder
  ): Notification {
    createNotificationChannel(channelId)
    return NotificationCompat.Builder(this, channelId)
      .setContentIntent(pendingIntent)
      .setPriority(NotificationCompat.PRIORITY_LOW)
      .setSmallIcon(com.lockwood.dwyw.ui.core.R.drawable.ic_launcher_foreground)
      .onBuild()
      .build()
  }

  protected fun createNotificationChannel(channelId: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val channel = NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_LOW)
      val notificationManager: NotificationManager =
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
      notificationManager.createNotificationChannel(channel)
    }
  }
}

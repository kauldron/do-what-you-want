package com.lockwood.room.base

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.lockwood.replicant.base.ReplicantService
import com.lockwood.room.RoomConnectionActivity

internal abstract class BaseRoomService : ReplicantService() {

    protected companion object {

        const val STOP_SERVICE = "stopService"
    }

    protected val pendingIntent: PendingIntent
        get() {
            val notificationIntent = Intent(this, RoomConnectionActivity::class.java)
            return PendingIntent.getActivity(this, 0, notificationIntent, 0)
        }

    protected inline fun buildNotification(
        channelId: String,
        onBuild: NotificationCompat.Builder.() -> NotificationCompat.Builder,
    ): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(channelId)
        }

        return NotificationCompat.Builder(this, channelId)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSmallIcon(com.lockwood.dwyw.ui.core.R.drawable.ic_broadcast)
            .onBuild()
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    protected fun createNotificationChannel(id: String) {
        val channel: NotificationChannel =
            NotificationChannel(id, id, NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

    protected fun stopForegroundSelf() {
        stopForeground(true)
        stopSelf()
    }

    protected fun isStopService(intent: Intent): Boolean {
        return intent.action.equals(STOP_SERVICE)
    }

}
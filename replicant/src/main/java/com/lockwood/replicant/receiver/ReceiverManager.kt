package com.lockwood.replicant.receiver

import android.content.Context

interface ReceiverManager {

	fun registerReceiver(context: Context)

	fun unregisterReceiver(context: Context)
}

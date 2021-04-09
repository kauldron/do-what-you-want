package com.lockwood.room.navigation.launcher

import android.content.Context
import com.lockwood.replicant.launcher.NoArgsLauncher
import com.lockwood.room.model.Room

interface IRoomsLauncher : NoArgsLauncher {

	fun launchAdvertising(context: Context)

	fun launchRoom(room: Room, context: Context)
}

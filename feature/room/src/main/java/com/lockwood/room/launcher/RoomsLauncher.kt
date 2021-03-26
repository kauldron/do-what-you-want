package com.lockwood.room.launcher

import android.content.Context
import android.os.Bundle
import com.lockwood.automata.android.launchActivity
import com.lockwood.replicant.launcher.Launcher
import com.lockwood.replicant.launcher.NoArgsLauncher
import com.lockwood.room.RoomConnectionActivity
import com.lockwood.room.launcher.RoomArgs.Companion.IS_SHOW_ADVERTISING
import com.lockwood.room.launcher.RoomArgs.Companion.ROOM_ID

interface IRoomsLauncher : NoArgsLauncher, Launcher<RoomArgs> {

  fun launchAdvertising(context: Context)
}

internal class RoomsLauncher : IRoomsLauncher {

  override fun launch(context: Context) {
    context.launchActivity<RoomConnectionActivity>()
  }

  override fun launchAdvertising(context: Context) {
    val argsBundle = Bundle().apply { putBoolean(IS_SHOW_ADVERTISING, true) }
    context.launchActivity<RoomConnectionActivity>(argsBundle)
  }

  override fun launch(context: Context, launchArgs: RoomArgs) {
    val argsBundle = Bundle().apply { putInt(ROOM_ID, launchArgs.roomId) }
    context.launchActivity<RoomConnectionActivity>(argsBundle)
  }
}

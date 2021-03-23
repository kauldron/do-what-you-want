package com.lockwood.room.launcher

import android.content.Context
import com.lockwood.automata.android.launchActivity
import com.lockwood.replicant.launcher.NoArgsLauncher
import com.lockwood.room.RoomConnectionActivity

internal class RoomsLauncher : NoArgsLauncher {

  override fun launch(context: Context) {
    context.launchActivity<RoomConnectionActivity>()
  }
}

package com.lockwood.room.rooms.launcher

import android.content.Context
import com.lockwood.automata.android.launchActivity
import com.lockwood.replicant.launcher.NoArgsLauncher
import com.lockwood.room.RoomActivity

class RoomsLauncher : NoArgsLauncher {

    override fun launch(context: Context) {
        context.launchActivity<RoomActivity>()
    }

}
package com.lockwood.room.ui.launcher

import android.content.Context
import com.lockwood.automata.android.launchActivity
import com.lockwood.replicant.launcher.ContextNoArgsLauncher
import com.lockwood.room.ui.activity.RoomActivity

class RoomsLauncher(context: Context) : ContextNoArgsLauncher<Context>(context) {

    override fun launch() {
        context.launchActivity<RoomActivity>()
    }

}
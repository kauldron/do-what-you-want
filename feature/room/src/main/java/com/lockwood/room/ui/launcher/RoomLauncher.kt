package com.lockwood.room.ui.launcher

import android.content.Context
import android.os.Bundle
import com.lockwood.automata.android.launchActivity
import com.lockwood.replicant.launcher.ContextLauncher
import com.lockwood.room.ui.activity.RoomActivity

// TODO: Use this launcher with deeplink handler
// TODO: Add QR-code generator to show in room for instant access to room
class RoomLauncher(context: Context) : ContextLauncher<Context, RoomArgs>(context) {

    override fun launch(launchArgs: RoomArgs) {
        val argsBundle = buildBundle(launchArgs)
        context.launchActivity<RoomActivity>(argsBundle)
    }

    override fun buildBundle(launchArgs: RoomArgs): Bundle = Bundle().apply {
        putInt(RoomArgs.ROOM_ID, launchArgs.roomId)
    }

}
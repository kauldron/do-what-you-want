package com.lockwood.room.navigation.launcher

import android.content.Context
import android.os.Bundle
import com.lockwood.automata.android.launchActivity
import com.lockwood.room.RoomConnectionActivity
import com.lockwood.room.model.Room
import com.lockwood.room.navigation.launcher.RoomArgs.IS_SHOW_ADVERTISING
import com.lockwood.room.navigation.launcher.RoomArgs.ROOM_TO_SHOW

internal class RoomsLauncher : IRoomsLauncher {

    override fun launch(context: Context) {
        context.launchActivity<RoomConnectionActivity>()
    }

    override fun launchAdvertising(context: Context) {
        val argsBundle = Bundle().apply { putBoolean(IS_SHOW_ADVERTISING, true) }
        context.launchActivity<RoomConnectionActivity>(argsBundle)
    }

    override fun launchRoom(room: Room, context: Context) {
        val argsBundle = Bundle().apply { putParcelable(ROOM_TO_SHOW, room) }
        context.launchActivity<RoomConnectionActivity>(argsBundle)
    }

}

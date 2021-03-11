package com.lockwood.room.launcher

import com.lockwood.replicant.launcher.args.LaunchArgs

inline class RoomArgs(val roomId: Int) : LaunchArgs {

    companion object {

        const val ROOM_ID = "roomId"

        @JvmStatic
        fun Int.toRoomArgs(): RoomArgs = RoomArgs(this)
    }
}
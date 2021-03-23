package com.lockwood.room.client.launcher

import com.lockwood.replicant.launcher.args.LaunchArgs

inline class RoomArgs(val roomId: Int) : LaunchArgs {

  companion object {

    internal const val ROOM_ID = "roomId"

    @JvmStatic fun Int.toRoomArgs(): RoomArgs = RoomArgs(this)
  }
}

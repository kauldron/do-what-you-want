package com.lockwood.room.launcher

import com.lockwood.replicant.launcher.args.LaunchArgs

inline class RoomArgs(val roomId: Int) : LaunchArgs {

  companion object {

    internal const val ROOM_ID = "roomId"
    internal const val IS_SHOW_ADVERTISING = "isShowAdvertising"
  }
}

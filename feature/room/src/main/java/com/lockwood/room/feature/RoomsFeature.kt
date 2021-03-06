package com.lockwood.room.feature

import com.lockwood.replicant.feature.ReleasableFeature
import com.lockwood.replicant.launcher.Launcher
import com.lockwood.replicant.launcher.NoArgsLauncher
import com.lockwood.replicant.releasable.notSafeReleasableLazy
import com.lockwood.room.data.IRoomRepository
import com.lockwood.room.data.RoomRepository
import com.lockwood.room.ui.launcher.RoomArgs
import com.lockwood.room.ui.launcher.RoomLauncher
import com.lockwood.room.ui.launcher.RoomsLauncher

class RoomsFeature : ReleasableFeature {

    val roomRepository: IRoomRepository by notSafeReleasableLazy { RoomRepository() }

    val roomLauncher: Launcher<RoomArgs>
        get() = RoomLauncher()

    val roomsLauncher: NoArgsLauncher
        get() = RoomsLauncher()

}
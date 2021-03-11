package com.lockwood.room.feature

import com.lockwood.replicant.feature.ReleasableFeature
import com.lockwood.replicant.launcher.Launcher
import com.lockwood.replicant.launcher.NoArgsLauncher
import com.lockwood.replicant.releasable.notSafeReleasableLazy
import com.lockwood.room.data.IRoomsInteractor
import com.lockwood.room.data.IRoomsRepository
import com.lockwood.room.data.RoomsInteractor
import com.lockwood.room.data.RoomsRepository
import com.lockwood.room.launcher.RoomArgs
import com.lockwood.room.launcher.RoomLauncher
import com.lockwood.room.launcher.RoomsLauncher

class RoomsFeature : ReleasableFeature {

    val roomsRepository: IRoomsRepository by notSafeReleasableLazy { RoomsRepository() }

    val roomsInteractor: IRoomsInteractor by notSafeReleasableLazy { RoomsInteractor(roomsRepository) }

    val roomLauncher: Launcher<RoomArgs>
        get() = RoomLauncher()

    val roomsLauncher: NoArgsLauncher
        get() = RoomsLauncher()

}
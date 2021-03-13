package com.lockwood.room.feature

import com.lockwood.replicant.feature.ReleasableFeature
import com.lockwood.replicant.launcher.Launcher
import com.lockwood.replicant.launcher.NoArgsLauncher
import com.lockwood.replicant.releasable.notSafeReleasableLazy
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.data.interactor.RoomsInteractor
import com.lockwood.room.data.repostiory.IRoomsRepository
import com.lockwood.room.data.repostiory.RoomsRepository
import com.lockwood.room.room.launcher.RoomArgs
import com.lockwood.room.room.launcher.RoomLauncher
import com.lockwood.room.rooms.launcher.RoomsLauncher

class RoomsFeature : ReleasableFeature {

    val roomsRepository: IRoomsRepository by notSafeReleasableLazy { RoomsRepository() }

    val roomsInteractor: IRoomsInteractor by notSafeReleasableLazy { RoomsInteractor(roomsRepository) }

    val roomLauncher: Launcher<RoomArgs>
        get() = RoomLauncher()

    val roomsLauncher: NoArgsLauncher
        get() = RoomsLauncher()

}
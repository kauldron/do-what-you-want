package com.lockwood.room.feature

import com.lockwood.connections.INearbyConnectionsManager
import com.lockwood.dwyw.core.wrapper.BuildConfigWrapper
import com.lockwood.player.IPlayerManager
import com.lockwood.replicant.feature.Feature
import com.lockwood.room.navigation.launcher.IRoomsLauncher
import com.lockwood.room.navigation.launcher.RoomsLauncher

class RoomsFeature(
    connectionsManager: INearbyConnectionsManager,
    playerManager: IPlayerManager,
    buildConfigWrapper: BuildConfigWrapper,
) : Feature {

//    private val roomsRepository: IRoomsRepository by notSafeLazy {
//        RoomsRepository(connectionsManager)
//    }
//
//    val roomsInteractor: IRoomsInteractor by lazy {
//        RoomsInteractor(
//            repository = roomsRepository,
//            playerManager = playerManager,
//            buildConfigWrapper = buildConfigWrapper
//        )
//    }

    val roomsLauncher: IRoomsLauncher
        get() = RoomsLauncher()

//    val roomsRouter: IRoomsRouter
//        get() = RoomsRouter(roomsInteractor)

}

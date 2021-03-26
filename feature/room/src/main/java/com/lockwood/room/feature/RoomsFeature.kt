package com.lockwood.room.feature

import androidx.lifecycle.ViewModelProvider
import com.lockwood.automata.core.notSafeLazy
import com.lockwood.connections.INearbyConnectionsManager
import com.lockwood.replicant.ext.notSafeReleasableLazy
import com.lockwood.replicant.feature.ReleasableFeature
import com.lockwood.room.RoomViewModelsFactory
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.data.interactor.RoomsInteractor
import com.lockwood.room.data.repostiory.IRoomsRepository
import com.lockwood.room.data.repostiory.RoomsRepository
import com.lockwood.room.launcher.IRoomsLauncher
import com.lockwood.room.launcher.RoomsLauncher

class RoomsFeature(connectionsManager: INearbyConnectionsManager) : ReleasableFeature {

  private val roomsRepository: IRoomsRepository by notSafeReleasableLazy {
    RoomsRepository(connectionsManager)
  }

  val roomsInteractor: IRoomsInteractor by notSafeReleasableLazy {
    RoomsInteractor(roomsRepository)
  }

  val viewModelsFactory: ViewModelProvider.Factory by notSafeLazy {
    RoomViewModelsFactory(roomsInteractor, connectionsManager)
  }

  val roomsLauncher: IRoomsLauncher
    get() = RoomsLauncher()
}

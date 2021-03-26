package com.lockwood.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lockwood.connections.INearbyConnectionsManager
import com.lockwood.room.client.ui.RoomsDiscoverViewModel
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.host.ui.RoomHostViewModel

@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
class RoomViewModelsFactory(
  private val roomsInteractor: IRoomsInteractor,
  private val connectionsManager: INearbyConnectionsManager
) : ViewModelProvider.Factory {

  @Suppress()
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return when {
      modelClass.isFrom<T, RoomsDiscoverViewModel>() ->
        RoomsDiscoverViewModel(roomsInteractor, connectionsManager)
      modelClass.isFrom<T, RoomHostViewModel>() -> RoomHostViewModel(roomsInteractor)
      else -> error("Unknown ViewModel class $modelClass")
    } as
      T
  }

  private inline fun <T, reified V : ViewModel> Class<T>.isFrom(): Boolean {
    return isAssignableFrom(V::class.java)
  }
}

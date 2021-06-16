package com.lockwood.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lockwood.connections.INearbyConnectionsManager
import com.lockwood.replicant.executor.provider.ExecutorProvider
import com.lockwood.room.data.interactor.IRoomsInteractor

@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
class RoomViewModelsFactory(
    private val roomsInteractor: IRoomsInteractor,
    private val connectionsManager: INearbyConnectionsManager,
    private val executorProvider: ExecutorProvider,
) : ViewModelProvider.Factory {

    @Suppress()
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
//			modelClass.isFrom<T, RoomsDiscoverViewModel>() -> RoomsDiscoverViewModel(
//					roomsInteractor = roomsInteractor,
//					connectionsManager = connectionsManager,
//					executorProvider = executorProvider
//			)
//			modelClass.isFrom<T, RoomHostViewModel>() -> RoomHostViewModel(
//					roomsInteractor = roomsInteractor,
//					connectionsManager = connectionsManager,
//					executorProvider = executorProvider
//			)
//			modelClass.isFrom<T, RoomClientViewModel>() -> RoomClientViewModel(
//					roomsInteractor = roomsInteractor,
//					connectionsManager = connectionsManager,
//					executorProvider = executorProvider
//			)
            else -> error("Unknown ViewModel class $modelClass")
        } as T
    }

    private inline fun <T, reified V : ViewModel> Class<T>.isFrom(): Boolean {
        return isAssignableFrom(V::class.java)
    }
}

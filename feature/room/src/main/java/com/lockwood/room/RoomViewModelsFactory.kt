package com.lockwood.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lockwood.connections.INearbyConnectionsManager
import com.lockwood.replicant.executor.provider.ExecutorProvider
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.feature.client.ui.RoomClientViewModel
import com.lockwood.room.feature.host.ui.RoomHostViewModel

@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
class RoomViewModelsFactory(
		@JvmField
		private val roomsInteractor: IRoomsInteractor,
		@JvmField
		private val connectionsManager: INearbyConnectionsManager,
		@JvmField
		private val executorProvider: ExecutorProvider,
) : ViewModelProvider.Factory {

	@Suppress()
	override fun <T : ViewModel?> create(modelClass: Class<T>): T {
		return when {
			modelClass.isFrom<T, RoomsDiscoverViewModel>() -> RoomsDiscoverViewModel(
					roomsInteractor = roomsInteractor,
					connectionsManager = connectionsManager,
					executorProvider = executorProvider
			)
			modelClass.isFrom<T, RoomHostViewModel>() -> RoomHostViewModel(
					roomsInteractor = roomsInteractor,
					connectionsManager = connectionsManager,
					executorProvider = executorProvider
			)
			modelClass.isFrom<T, RoomClientViewModel>() -> RoomClientViewModel(
					roomsInteractor = roomsInteractor,
					connectionsManager = connectionsManager,
					executorProvider = executorProvider
			)
			else -> error("Unknown ViewModel class $modelClass")
		} as T
	}

	private inline fun <T, reified V : ViewModel> Class<T>.isFrom(): Boolean {
		return isAssignableFrom(V::class.java)
	}
}

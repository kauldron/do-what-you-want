package com.lockwood.room.base

import androidx.annotation.CallSuper
import com.lockwood.connections.INearbyConnectionsManager
import com.lockwood.connections.callback.ConnectionCallback
import com.lockwood.connections.callback.DiscoveryCallback
import com.lockwood.connections.callback.PayloadCallback
import com.lockwood.dwyw.core.BaseViewModel
import com.lockwood.replicant.executor.ExecutorProvider
import com.lockwood.replicant.state.ViewState
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.model.Room

internal abstract class BaseConnectionViewModel<VS : ViewState>(
		protected val roomsInteractor: IRoomsInteractor,
		protected val connectionsManager: INearbyConnectionsManager,
		executorProvider: ExecutorProvider,
		initState: VS
) : BaseViewModel<VS>(initState, executorProvider) {

	@CallSuper
	open fun requestConnection(item: Room) {
		roomsInteractor.requestConnection(item.endpointId)
	}

	@CallSuper
	open fun acceptConnection(item: Room) {
		roomsInteractor.acceptConnection(item.endpointId)
	}

	@CallSuper
	open fun rejectConnection(item: Room) {
		roomsInteractor.rejectConnection(item.endpointId)
	}

	protected fun <L : Lazy<DiscoveryCallback>> addDiscoveryCallback(callback: L) {
		if (!callback.isInitialized()) {
			connectionsManager.addDiscoveryCallback(callback.value)
		}
	}

	protected fun <L : Lazy<ConnectionCallback>> addConnectionCallback(callback: L) {
		if (!callback.isInitialized()) {
			connectionsManager.addConnectionCallback(callback.value)
		}
	}

	protected fun <L : Lazy<PayloadCallback>> addPayloadCallback(callback: L) {
		if (!callback.isInitialized()) {
			connectionsManager.addPayloadCallback(callback.value)
		}
	}

	protected fun removeConnectionCallback(callback: ConnectionCallback) {
		connectionsManager.removeConnectionCallback(callback)
	}

}
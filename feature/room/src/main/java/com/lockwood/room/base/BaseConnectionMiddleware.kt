package com.lockwood.room.base

import androidx.annotation.CallSuper
import com.lockwood.connections.INearbyConnectionsManager
import com.lockwood.connections.callback.ConnectionCallback
import com.lockwood.replicant.mvi.base.BaseMiddleWare
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.model.Room

internal abstract class BaseConnectionMiddleware<State, Action>(
    @JvmField
    protected val roomsInteractor: IRoomsInteractor,
    @JvmField
    protected val connectionsManager: INearbyConnectionsManager,
) : BaseMiddleWare<State, Action>() {

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

    protected fun <L : Lazy<ConnectionCallback>> addConnectionCallback(callback: L) {
        if (!callback.isInitialized()) {
            connectionsManager.addConnectionCallback(callback.value)
        }
    }

    protected fun removeConnectionCallback(callback: ConnectionCallback) {
        connectionsManager.removeConnectionCallback(callback)
    }

}
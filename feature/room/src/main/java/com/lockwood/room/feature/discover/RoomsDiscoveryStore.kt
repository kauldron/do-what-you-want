package com.lockwood.room.feature.discover

import com.lockwood.connections.INearbyConnectionsManager
import com.lockwood.replicant.mvi.base.BaseStore
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.feature.discover.middleware.RoomsDiscoveryMiddleware

internal class RoomsDiscoveryStore(
		roomsInteractor: IRoomsInteractor,
		connectionsManager: INearbyConnectionsManager,
		initialState: RoomsDiscoverViewState = RoomsDiscoverViewState.initialState,
) : BaseStore<RoomsDiscoverViewState, RoomsDiscoveryAction, RoomsDiscoveryEffects>(
		reducer = RoomsDiscoverReducer(),
		middlewares = listOf(RoomsDiscoveryMiddleware(roomsInteractor, connectionsManager)),
		initialState = initialState,
)
package com.lockwood.room.navigation.router

import com.lockwood.replicant.screen.Screen
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.screen.AdvertisingScreen
import com.lockwood.room.screen.ConnectionScreen
import com.lockwood.room.screen.DiscoveryScreen

internal class RoomsRouter(
		@JvmField
		private val roomsInteractor: IRoomsInteractor,
) : IRoomsRouter {

	override fun getScreenToShow(): Screen = when {
		roomsInteractor.isBroadcasting -> AdvertisingScreen
		roomsInteractor.isConnected -> ConnectionScreen(roomsInteractor.connectedRoom)
		else -> DiscoveryScreen
	}

}
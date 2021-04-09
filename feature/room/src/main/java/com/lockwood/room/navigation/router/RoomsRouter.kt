package com.lockwood.room.navigation.router

import com.lockwood.replicant.screen.Screen
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.screen.RoomConnectionScreen
import com.lockwood.room.screen.RoomsAdvertisingScreen
import com.lockwood.room.screen.RoomsDiscoveryScreen

internal class RoomsRouter(
		private val roomsInteractor: IRoomsInteractor
) : IRoomsRouter {

	override fun getScreenToShow(): Screen = when {
		roomsInteractor.isBroadcasting -> RoomsAdvertisingScreen
		roomsInteractor.isConnected -> RoomConnectionScreen(roomsInteractor.connectedRoom)
		else -> RoomsDiscoveryScreen
	}

}
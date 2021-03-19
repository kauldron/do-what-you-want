package com.lockwood.room.data.interactor

import com.lockwood.room.data.Room
import com.lockwood.room.data.repostiory.IRoomsRepository

internal class RoomsInteractor(
		private val repository: IRoomsRepository,
) : IRoomsInteractor, IRoomsRepository by repository {

	override fun fetchRoom(id: Int): Room? {
		return fetchRooms().find { room -> room.id == id }
	}

}
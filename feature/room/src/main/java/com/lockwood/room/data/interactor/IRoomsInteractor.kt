package com.lockwood.room.data.interactor

import com.lockwood.room.data.Room
import com.lockwood.room.data.repostiory.IRoomsRepository

interface IRoomsInteractor : IRoomsRepository {

	fun fetchRoom(id: Int): Room?

}
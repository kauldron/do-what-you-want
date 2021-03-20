package com.lockwood.room.data.repostiory

import com.lockwood.room.data.Room

interface IRoomsRepository {

	var test: String?

	fun fetchRooms(): List<Room>

}
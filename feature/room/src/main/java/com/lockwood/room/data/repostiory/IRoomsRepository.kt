package com.lockwood.room.data.repostiory

import com.lockwood.room.data.Room

interface IRoomsRepository {

    fun fetchRooms(): List<Room>

}
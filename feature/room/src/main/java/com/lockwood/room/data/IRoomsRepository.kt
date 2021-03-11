package com.lockwood.room.data

interface IRoomsRepository {

    fun fetchRooms(): List<Room>

}
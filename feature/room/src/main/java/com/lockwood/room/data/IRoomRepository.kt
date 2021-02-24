package com.lockwood.room.data

interface IRoomRepository {

    fun fetchRooms(): List<Room>

}
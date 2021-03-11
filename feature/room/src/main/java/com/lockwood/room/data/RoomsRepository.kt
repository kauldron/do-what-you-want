package com.lockwood.room.data

class RoomsRepository : IRoomsRepository {

    override fun fetchRooms(): List<Room> {
        return listOf(
            Room(0, "Test 0"),
            Room(1, "Test 1"),
            Room(2, "Test 2"),
            Room(3, "Test 3"),
        )
    }

}
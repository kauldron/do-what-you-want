package com.lockwood.room.data

class RoomsInteractor(
    private val repository: IRoomsRepository
) : IRoomsInteractor, IRoomsRepository by repository {

    override fun fetchRoom(id: Int): Room? {
        return fetchRooms().find { room -> room.id == id }
    }

}
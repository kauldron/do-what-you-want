package com.lockwood.room.data

interface IRoomsInteractor : IRoomsRepository {

    fun fetchRoom(id: Int): Room?

}
package com.lockwood.room.data.interactor

import com.lockwood.connections.model.EndpointId
import com.lockwood.room.data.repostiory.IRoomsRepository
import com.lockwood.room.model.Room

interface IRoomsInteractor : IRoomsRepository {

    fun requestConnection(endpointId: EndpointId)

    fun isSameHostRoom(room: Room): Boolean

}

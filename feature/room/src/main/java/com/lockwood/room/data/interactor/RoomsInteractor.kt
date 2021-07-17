package com.lockwood.room.data.interactor

import com.lockwood.connections.model.EndpointId
import com.lockwood.dwyw.core.wrapper.BuildConfigWrapper
import com.lockwood.player.IPlayerManager
import com.lockwood.room.data.repostiory.IRoomsRepository
import com.lockwood.room.model.Room

internal class RoomsInteractor(
    private val repository: IRoomsRepository,
    private val playerManager: IPlayerManager,
    private val buildConfigWrapper: BuildConfigWrapper,
) :
    IRoomsInteractor,
    IPlayerManager by playerManager,
    IRoomsRepository by repository {

    override fun requestConnection(endpointId: EndpointId) {
        repository.requestConnection(buildConfigWrapper.deviceModel, endpointId)
    }

    override fun isSameHostRoom(room: Room): Boolean {
        return room.name == buildConfigWrapper.deviceModel
    }

}
